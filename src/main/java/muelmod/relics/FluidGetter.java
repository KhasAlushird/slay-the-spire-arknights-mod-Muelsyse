package muelmod.relics;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;
import muelmod.powers.Fluid;

// 继承CustomRelic
public class FluidGetter extends CustomRelic {
    // 遗物ID
    public static final String ID = IdHelper.makePath("FluidGetter");
    // 图片路径
    private static final String IMG_PATH =ImageHelper.getOtherImgPath("relics", "fluidgetter");
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.UNCOMMON;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.HEAVY;

    public FluidGetter() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
 public void atBattleStart() {
    this.counter = 0;
  }
  
  public void atTurnStart() {
    if (!this.grayscale)
      this.counter++; 
    if (this.counter == 2) {
      flash();
      addToBot((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, this));
      addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Fluid(AbstractDungeon.player, 3), 3));
      this.counter = -1;
      this.grayscale = true;
    } 
  }
  
  public void onVictory() {
    this.counter = -1;
    this.grayscale = false;
  }

    public AbstractRelic makeCopy() {
        return new FluidGetter();
    }
}
