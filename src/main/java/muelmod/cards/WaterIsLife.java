package muelmod.cards;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import muelmod.actions.VampireDamageSingleEnemyAction;
import muelmod.character.MuelSyse;
import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;
import muelmod.powers.Fluid;

public class WaterIsLife extends CustomCard {

    public static final String ID = IdHelper.makePath("WaterIsLife");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ImageHelper.getCardImgPath(CardType.ATTACK, "WaterIsLife");
    private static final int COST = 0;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int fluid_demand = 1;

    public WaterIsLife() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 4;
        this.exhaust = true;
        this.tags.add(AbstractCard.CardTags.HEALING);
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
 
             this.addToBot(new VampireDamageSingleEnemyAction(m, p, this.damage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
 
         }
         else{
            this.addToBot(
                new DamageAction(
                    m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn))
            );
            
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
      return new WaterIsLife();
   }

    
}
