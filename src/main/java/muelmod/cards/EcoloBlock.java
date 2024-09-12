package muelmod.cards;

import java.util.Iterator;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import muelmod.actions.AdditionalBlockAction;
import muelmod.character.MuelSyse;
import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;

public class EcoloBlock extends CustomCard {

    public static final String ID = IdHelper.makePath("EcoloBlock");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH =  ImageHelper.getCardImgPath(CardType.SKILL, "EcoloBlock");
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public EcoloBlock() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 5;
        this.magicNumber = this.baseMagicNumber = 12;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.upgradedMagicNumber = this.upgradedBlock;
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        boolean hasAttackCard = false;
        Iterator<AbstractCard> var4 = p.hand.group.iterator();

        while (var4.hasNext()) {
            AbstractCard c = var4.next();
            if (c.type == CardType.ATTACK) {
                hasAttackCard = true;
                break;
            }
        }

        if (!hasAttackCard) {
            this.addToBot(new AdditionalBlockAction(p, this.block, this.magicNumber));
        }
        else{
            this.addToBot(new AdditionalBlockAction(p, this.block, 0));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        boolean glow = true;
        Iterator<AbstractCard> var2 = AbstractDungeon.player.hand.group.iterator();

        while (var2.hasNext()) {
            AbstractCard c = var2.next();
            if (c.type == CardType.ATTACK && c != this) {
                glow = false;
                break;
            }
        }

        if (glow) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void applyPowers() {
            //this.baseBlock += 7 + this.timesUpgraded * 0;    7是baseMagicNumber-baseBlock,0是baseMagicNumber升级提升的数值
            //this.baseMagicNumber = this.baseBlock - this.timesUpgraded * 4;  
            //关于上面这一条，如果不加- this.timesUpgraded * 4，那么baseMagicNumber还会增加baseBlock提升的4点数值
            this.baseBlock += 7 + this.timesUpgraded * 0;
            this.baseMagicNumber = this.baseBlock - this.timesUpgraded * 4;
            super.applyPowers();
            this.magicNumber = this.block -this.timesUpgraded * 4;
            this.isMagicNumberModified = this.isBlockModified;
            this.baseBlock -= 7 + this.timesUpgraded * 0;
            super.applyPowers();
    }

    public AbstractCard makeCopy() {
      return new EcoloBlock();
   }

}
    

