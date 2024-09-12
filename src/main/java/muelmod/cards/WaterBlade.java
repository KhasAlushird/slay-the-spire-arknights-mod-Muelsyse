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
import muelmod.powers.BlockFluid;

public class WaterBlade extends CustomCard {

    public static final String ID = IdHelper.makePath("WaterBlade");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ImageHelper.getCardImgPath(CardType.ATTACK, "WaterBlade");
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int BlockFluid_demand = 1;

    public WaterBlade() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 8;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
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
         int curr_BlockFluid_amount = -1;
         if(p.hasPower(BlockFluid.POWER_ID)){
             curr_BlockFluid_amount =  p.getPower(BlockFluid.POWER_ID).amount;   
         }
         if (this.BlockFluid_demand <= curr_BlockFluid_amount){
             if(this.BlockFluid_demand < curr_BlockFluid_amount){
                 this.addToBot(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(p, p, BlockFluid.POWER_ID, this.BlockFluid_demand));
             }
             else{
                 this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, BlockFluid.POWER_ID));
             }

             this.addToBot(
                new DamageAction(
                    m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn))
            ); 
            this.addToBot(
                new DamageAction(
                    m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn))
            ); 
         }

    }



    @Override
    public void triggerOnGlowCheck() {
        boolean glow = false;
        AbstractPlayer p = com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
        int curr_AttackBlockFluid_amount = -1;
        if (p.hasPower(BlockFluid.POWER_ID)){
            curr_AttackBlockFluid_amount=p.getPower(BlockFluid.POWER_ID).amount;
        }
        if (this.BlockFluid_demand <= curr_AttackBlockFluid_amount){
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
      return new WaterBlade();
   }

    
}
