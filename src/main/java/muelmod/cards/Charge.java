package muelmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import muelmod.character.MuelSyse;
import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;
import muelmod.powers.EnergyFluid;
import muelmod.powers.Fluid;

public class Charge extends CustomCard {

    public static final String ID = IdHelper.makePath("Charge");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH =   ImageHelper.getCardImgPath(CardType.SKILL, "Charge");
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int fluid_demand = 2;

    public Charge() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
       this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int energyfluid_to_add=0;
        if(this.upgraded){
            energyfluid_to_add+=1;
        }
        if(p.hasPower(Fluid.POWER_ID)){
            int fluid_amount=p.getPower(Fluid.POWER_ID).amount;
            int fluid_to_transfer=fluid_amount/2;
            energyfluid_to_add+=fluid_to_transfer;
            if(fluid_amount%2==1){
                this.addToBot(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(p, p, Fluid.POWER_ID, fluid_to_transfer*2));
            }
            else{
                this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, Fluid.POWER_ID));
            }
        }
         this.addToBot(new ApplyPowerAction(p, p, new EnergyFluid(p, energyfluid_to_add), energyfluid_to_add));
    }

     public AbstractCard makeCopy() {
      return new Charge();
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


    
}
