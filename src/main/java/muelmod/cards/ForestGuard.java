package muelmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
import muelmod.powers.Fluid;

public class ForestGuard extends CustomCard {

    public static final String ID = IdHelper.makePath("ForestGuard");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH =  ImageHelper.getCardImgPath(CardType.SKILL, "ForestGuard");
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private int bio_demand = 1;
    private int fluid_demand = 2;
    public ForestGuard() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = 5;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
       this.addToBot(new GainBlockAction(p, p, this.block));
        //标准的使用N点流形的代码
            
        int curr_fluid_amount = -1;
        int curr_bio_amount = -1;
        if (p.hasPower(Fluid.POWER_ID)){
            curr_fluid_amount=p.getPower(Fluid.POWER_ID).amount;
        }
        if(p.hasPower(Bio.POWER_ID)){
            curr_bio_amount =  p.getPower(Bio.POWER_ID).amount;   
        }   


        if (this.bio_demand <= curr_bio_amount){
            //当有2点bio时候
            if (this.fluid_demand <= curr_fluid_amount){
                if(this.fluid_demand < curr_fluid_amount){
                    this.addToBot(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(p, p, Fluid.POWER_ID, this.fluid_demand));
                }
                else{
                    this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, Fluid.POWER_ID));
                }

                this.addToBot(new ApplyPowerAction(p, p, new AttackFluid(p, 2), 2));

            }
            //当有1点bio时候
            if(this.upgraded && curr_fluid_amount ==1){
                this.addToBot(new ApplyPowerAction(p, p, new AttackFluid(p, 1), 1));
                this.addToBot(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(p, p, Fluid.POWER_ID));
            }
    }
}



    
      @Override
        public void triggerOnGlowCheck() {
            boolean glow = false;
            AbstractPlayer p = com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

            
            int curr_fluid_amount = -1;
            int curr_bio_amount = -1;
            if (p.hasPower(Fluid.POWER_ID)){
                curr_fluid_amount=p.getPower(Fluid.POWER_ID).amount;
            }
            if(p.hasPower(Bio.POWER_ID)){
                curr_bio_amount =  p.getPower(Bio.POWER_ID).amount;   
            }   

            
            if (this.bio_demand <= curr_bio_amount){
                if (this.fluid_demand <= curr_fluid_amount){
                    glow = true;
                }
                else if(this.upgraded && curr_fluid_amount ==1){
                    glow = true;
                }
                else{
                    glow = false;
                }
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
      return new ForestGuard();
   }

    
}
