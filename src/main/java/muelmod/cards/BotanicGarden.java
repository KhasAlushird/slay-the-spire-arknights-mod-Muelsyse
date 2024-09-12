package muelmod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import muelmod.character.MuelSyse;
import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;
import muelmod.powers.Bio;

public class BotanicGarden extends CustomCard {

    public static final String ID = IdHelper.makePath("BotanicGarden");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ImageHelper.getCardImgPath(CardType.SKILL, "BotanicGarden");
    private static final int COST = 3;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = MuelSyse.PlayerColorEnum.MUEL_COLOR;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private int bio_demand;
    private boolean glow = false;
    public BotanicGarden() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        bio_demand = 3;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c =  CardLibrary.getAnyColorCard( CardRarity.RARE );
        c.setCostForTurn(0);
        addToBot(new MakeTempCardInHandAction(c, true));

        AbstractCard b =  CardLibrary.getAnyColorCard( CardRarity.RARE );
        b.setCostForTurn(0);
        addToBot(new MakeTempCardInHandAction(b, true));

        AbstractCard a =  CardLibrary.getAnyColorCard( CardRarity.RARE );
        a.setCostForTurn(0);
        addToBot(new MakeTempCardInHandAction(a, true));
    }

     public AbstractCard makeCopy() {
      return new BotanicGarden();
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



    
}
