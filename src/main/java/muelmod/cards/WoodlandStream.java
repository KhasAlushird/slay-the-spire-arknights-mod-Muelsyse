package muelmod.cards;

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
import muelmod.powers.BlockFluid;
import muelmod.powers.EnergyFluid;
import muelmod.powers.Fluid;

public class WoodlandStream extends CustomCard {

    public static final String ID = IdHelper.makePath("WoodlandStream");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ImageHelper.getCardImgPath(CardType.SKILL, "WoodlandStream");
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private int bio_demand = 1;

    public WoodlandStream() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber=this.magicNumber=1;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int card_draw_amount=this.magicNumber;
        if(p.hasPower(Fluid.POWER_ID)){
            card_draw_amount += p.getPower(Fluid.POWER_ID).amount;
        }
        if(p.hasPower(AttackFluid.POWER_ID)){
            card_draw_amount += p.getPower(AttackFluid.POWER_ID).amount;
        }
        if(p.hasPower(BlockFluid.POWER_ID)){
            card_draw_amount += p.getPower(BlockFluid.POWER_ID).amount;
        }
        if(p.hasPower(EnergyFluid.POWER_ID)){
            card_draw_amount += p.getPower(EnergyFluid.POWER_ID).amount;
        }
        this.addToBot(new com.megacrit.cardcrawl.actions.common.DrawCardAction(p, card_draw_amount));
        
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse)
          return false; 
         //标准的使用N点生态的代码
         int curr_bio_amount = -1;
         if(p.hasPower(Bio.POWER_ID)){
             curr_bio_amount =  p.getPower(Bio.POWER_ID).amount;   
         }
         if (this.bio_demand > curr_bio_amount){
             canUse = false;
         }
        return canUse;
      }

    @Override
        public void triggerOnGlowCheck() {
            boolean glow = false;
            AbstractPlayer p = com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
            int curr_bio_amount = -1;
            if (p.hasPower(Bio.POWER_ID)){
                curr_bio_amount=p.getPower(Bio.POWER_ID).amount;
            }
            if (this.bio_demand <= curr_bio_amount){
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
      return new WoodlandStream();
   }

    
}
