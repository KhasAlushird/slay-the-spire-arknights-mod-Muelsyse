package muelmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import muelmod.character.MuelSyse;
import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;
import muelmod.powers.AttackFluid;

public class Solidify extends CustomCard {

    public static final String ID = IdHelper.makePath("Solidify");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ImageHelper.getCardImgPath(CardType.ATTACK, "Solidify");
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int AttackFluid_demand = 1;

    public Solidify() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 8;
        this.baseMagicNumber = this.magicNumber = 3;
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
        this.addToBot(
            new DamageAction(
                m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn))
        );

        
         //标准的使用N点流形的代码
         int curr_AttackFluid_amount = -1;
         if(p.hasPower(AttackFluid.POWER_ID)){
             curr_AttackFluid_amount =  p.getPower(AttackFluid.POWER_ID).amount;   
         }
         if (this.AttackFluid_demand <= curr_AttackFluid_amount){
             if(this.AttackFluid_demand < curr_AttackFluid_amount){
                 this.addToBot(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(p, p, AttackFluid.POWER_ID, this.AttackFluid_demand));
             }
             else{
                 this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, AttackFluid.POWER_ID));
             }
             addToBot(new ApplyPowerAction(p,p, new StrengthPower(p, this.magicNumber), this.magicNumber));

             
         }

    }



    @Override
    public void triggerOnGlowCheck() {
        boolean glow = false;
        AbstractPlayer p = com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
        int curr_AttackAttackFluid_amount = -1;
        if (p.hasPower(AttackFluid.POWER_ID)){
            curr_AttackAttackFluid_amount=p.getPower(AttackFluid.POWER_ID).amount;
        }
        if (this.AttackFluid_demand <= curr_AttackAttackFluid_amount){
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
      return new Solidify();
   }

    
}
