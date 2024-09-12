package muelmod.cards;

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
import muelmod.powers.Fluid;
//!!如果要改这样卡的数值，请务必修改applyPowers()函数，否则会出现数值错误的问题
public class FluidShot extends CustomCard {

    public static final String ID = IdHelper.makePath("FluidShot");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ImageHelper.getCardImgPath(CardType.ATTACK, "FluidShot");
    private static final int COST = 2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private int fluid_demand;
    private boolean glow = false;

    public FluidShot() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 15;
        this.baseMagicNumber=this.magicNumber=10;
        this.fluid_demand =2;

    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(5);
            this.upgradeMagicNumber(4);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.upgradedMagicNumber = this.upgradedDamage;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
            new DamageAction(
                m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn))
        );

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
            
            // float final_magic_damage=(float)this.magicNumber;
            // if(m.hasPower(VulnerablePower.POWER_ID)){
            //     final_magic_damage*=1.5;
            // }
            // if(p.hasPower(WeakPower.POWER_ID)){
            //     final_magic_damage*=0.75;
            // }
            // if(m.hasPower(FlightPower.POWER_ID)){
            //     final_magic_damage*=0.5;
            // }
            // this.addToBot(new DamageAction(m, new DamageInfo(p, (int)final_magic_damage, this.damageTypeForTurn)));
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.magicNumber, this.damageTypeForTurn)));

        }
    }

    //流形消耗牌标准的发光代码
    @Override
    public void triggerOnGlowCheck() {
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


    
    public void applyPowers() {
        //this.baseBlock += 7 + this.timesUpgraded * 0;    7是baseMagicNumber-baseBlock,0是baseMagicNumber升级提升的数值
        //this.baseMagicNumber = this.baseBlock - this.timesUpgraded * 4;  
        //关于上面这一条，如果不加- this.timesUpgraded * 4，那么baseMagicNumber还会增加baseBlock提升的4点数值
        this.baseDamage += -5 + this.timesUpgraded * 4;
        this.baseMagicNumber = this.baseDamage - this.timesUpgraded * 5;
        super.applyPowers();
        this.magicNumber = this.damage -this.timesUpgraded * 5;
        this.isMagicNumberModified = this.isDamageModified;
        this.baseDamage -= -5 + this.timesUpgraded * 4;
        super.applyPowers();
}
     public AbstractCard makeCopy() {
      return new FluidShot();
   }

    
}
