package muelmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;

public class CambrianPower extends AbstractPower {
  public static final String POWER_ID = IdHelper.makePath("CambrianPower");
  
  private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
  
  public static final String NAME = powerStrings.NAME;
  
  public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
  
  private int att_and_block_flu_amount = 0;
  
  private static int CambrianIdOffset;
  
  public CambrianPower(AbstractCreature owner, int turns, int att_and_block_flu_amount) {
    this.name = NAME;
    this.ID = POWER_ID + CambrianIdOffset;
    CambrianIdOffset++;
    this.owner = owner;
    this.amount = turns;
    this.att_and_block_flu_amount = att_and_block_flu_amount;

    
    // 添加一大一小两张能力图
    String path128 = ImageHelper.getOtherImgPath("powers","cambrianpower_84");
    String path48 = ImageHelper.getOtherImgPath("powers","cambrianpower_32");
    this.region128 = new AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
    this.region48 = new AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);


    updateDescription();
  }
  
  public void atEndOfTurn(boolean isPlayer) {
    if(this.amount > 1){
        addToBot((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, this, 1));
    }
    else if(this.amount == 1){
        addToBot(new RemoveSpecificPowerAction(this.owner,this.owner, this));
        addToBot(new ApplyPowerAction(this.owner,this.owner,new EnergizedPower(this.owner, 3), 3));
        addToBot(new ApplyPowerAction(this.owner, this.owner, new Bio(this.owner, 3), 3));
        if(this.att_and_block_flu_amount > 0){
            addToBot(new ApplyPowerAction(this.owner, this.owner, new BlockFluid(this.owner, this.att_and_block_flu_amount), this.att_and_block_flu_amount));
            addToBot(new ApplyPowerAction(this.owner, this.owner, new AttackFluid(this.owner, this.att_and_block_flu_amount), this.att_and_block_flu_amount));

        }
    }
  }
  
  public void updateDescription() {
    this.description = String.format(DESCRIPTIONS[0], this.amount,this.att_and_block_flu_amount); // 这样，%d就被替换成能力的层数
  }
}
