package muelmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import basemod.abstracts.CustomCard;
import muelmod.character.MuelSyse;
import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;
import muelmod.powers.Bio;
import muelmod.powers.Fluid;

public class AcidRain extends CustomCard {

    public static final String ID = IdHelper.makePath("AcidRain");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ImageHelper.getCardImgPath(CardType.ATTACK, "AcidRain");
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private int fluid_demand = 1;
    public AcidRain() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 10;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //fluid 相关
        int curr_fluid_amount = -1;
        if(p.hasPower(Fluid.POWER_ID)){
            curr_fluid_amount =  p.getPower(Fluid.POWER_ID).amount;   
        }
        if (this.fluid_demand <= curr_fluid_amount){
        if(this.fluid_demand < curr_fluid_amount){
            this.addToBot(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(p, p, Fluid.POWER_ID, this.fluid_demand));
        }
        else{
            this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, Fluid.POWER_ID));
        }

        //造成伤害
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

  }

    //debuff相关
    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
        addToBot(new ApplyPowerAction(mo, p,new VulnerablePower(mo, 2, false), 2, true, AbstractGameAction.AttackEffect.NONE)); 
        if (this.upgraded){
            addToBot(new ApplyPowerAction(mo, p,new VulnerablePower(mo, 2, false), 2, true, AbstractGameAction.AttackEffect.NONE)); 
        }
        
    }
}

@Override
public void triggerOnGlowCheck() {
    boolean glow = false;
    AbstractPlayer p = com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
    int curr_fluid_amount = -1;
    if (p.hasPower(Fluid.POWER_ID)){
        curr_fluid_amount=p.getPower(Fluid.POWER_ID).amount;
    }
    if (this.fluid_demand <= curr_fluid_amount){
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
      return new AcidRain();
   }

    
}
