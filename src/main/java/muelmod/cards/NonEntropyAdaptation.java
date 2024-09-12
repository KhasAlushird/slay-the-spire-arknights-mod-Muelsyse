package muelmod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import muelmod.character.MuelSyse;
import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;

public class NonEntropyAdaptation extends CustomCard {

    public static final String ID = IdHelper.makePath("NonEntropyAdaptation");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ImageHelper.getCardImgPath(CardType.SKILL, "NonEntropyAdaptation");
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public NonEntropyAdaptation() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToTop(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
        //exchange the amount of attackfluid and blockfluid
        if(p.hasPower("MuelSyseKhas:AttackFluid")||p.hasPower("MuelSyseKhas:BlockFluid")){
            int attackFluid = 0;
            int blockFluid = 0;
            if(p.hasPower("MuelSyseKhas:AttackFluid")){
                attackFluid = p.getPower("MuelSyseKhas:AttackFluid").amount;
            }
            if(p.hasPower("MuelSyseKhas:BlockFluid")){
                blockFluid = p.getPower("MuelSyseKhas:BlockFluid").amount;
            }
            if(attackFluid>0){
                this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, "MuelSyseKhas:AttackFluid"));
            }
            if(blockFluid>0){
                this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, "MuelSyseKhas:BlockFluid"));
            }
            if(attackFluid>0){
                this.addToBot(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new muelmod.powers.BlockFluid(p, attackFluid), attackFluid));
            }
            if(blockFluid>0){
                this.addToBot(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new muelmod.powers.AttackFluid(p, blockFluid), blockFluid));
            }
        }
       
    }


     public AbstractCard makeCopy() {
      return new NonEntropyAdaptation();
   }

    
}
