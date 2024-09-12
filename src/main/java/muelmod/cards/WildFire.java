package muelmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import muelmod.character.MuelSyse;
import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;
import muelmod.powers.AttackFluid;
import muelmod.powers.Bio;
import muelmod.powers.BlockFluid;
import muelmod.powers.EnergyFluid;

public class WildFire extends CustomCard {

    public static final String ID = IdHelper.makePath("WildFire");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ImageHelper.getCardImgPath(CardType.ATTACK, "WildFire");
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    public WildFire() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 28;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(7);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
            new DamageAction(
                m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn))
        );
        int energyfluid_to_add=0;
        if(p.hasPower(AttackFluid.POWER_ID)){
            energyfluid_to_add += p.getPower(AttackFluid.POWER_ID).amount;
            this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, AttackFluid.POWER_ID));
        }
        if(p.hasPower(BlockFluid.POWER_ID)){
            energyfluid_to_add += p.getPower(BlockFluid.POWER_ID).amount;
            this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, BlockFluid.POWER_ID));
        }
        if(energyfluid_to_add>0){
            this.addToBot(new ApplyPowerAction(p, p, new EnergyFluid(p, energyfluid_to_add), energyfluid_to_add));
        }
}

@Override
public void triggerOnGlowCheck() {
    boolean glow = false;
    AbstractPlayer p = com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
    if (p.hasPower(AttackFluid.POWER_ID)||p.hasPower(BlockFluid.POWER_ID)){
        glow = true;
    }
    else{
        glow = false;
    }
    if (glow) {
        this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
    } else {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }
}


public boolean canUse(AbstractPlayer p, AbstractMonster m) {
    boolean canUse = super.canUse(p, m);
    if (!canUse)
      return false; 
     if(p.hasPower(Bio.POWER_ID)){
        canUse = false; 
     }
    return canUse;
  }
     public AbstractCard makeCopy() {
      return new WildFire();
   }

    
}
