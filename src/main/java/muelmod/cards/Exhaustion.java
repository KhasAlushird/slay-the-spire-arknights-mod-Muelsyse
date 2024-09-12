package muelmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import muelmod.character.MuelSyse;
import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;
import muelmod.powers.EnergyFluid;
import muelmod.powers.Fluid;

public class Exhaustion extends CustomCard {

    public static final String ID = IdHelper.makePath("Exhaustion");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ImageHelper.getCardImgPath(CardType.ATTACK, "Exhaustion");
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int fluid_demand = 1;

    public Exhaustion() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 6;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
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
        this.addToTop(new DrawCardAction(AbstractDungeon.player, 1));

          //标准的使用N点流形的代码
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
  
               this.addToBot(new ApplyPowerAction(p, p, new EnergyFluid(p, 1), 1));
  
          }
        //remove fluid and Bio
        if(p.hasPower(Fluid.POWER_ID)){
            this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, Fluid.POWER_ID));
        }
        if(p.hasPower(muelmod.powers.Bio.POWER_ID)){
            this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, muelmod.powers.Bio.POWER_ID));
        }
    }

        //流形消耗牌标准的发光代码
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

     public AbstractCard makeCopy() {
      return new Exhaustion();
   }

    
}
