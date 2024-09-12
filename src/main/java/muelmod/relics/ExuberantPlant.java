package muelmod.relics;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import static com.megacrit.cardcrawl.events.AbstractEvent.logMetricRelicSwap;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;
import muelmod.powers.Bio;
import muelmod.powers.Fluid;

// 继承CustomRelic
public class ExuberantPlant extends CustomRelic {
    // 遗物ID
    public static final String ID = IdHelper.makePath("ExuberantPlant");
    // 图片路径
    private static final String IMG_PATH =ImageHelper.getOtherImgPath("relics", "exuberantplant");
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.BOSS;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public ExuberantPlant() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
       
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractCreature p = AbstractDungeon.player;
        super.atBattleStart();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this. addToBot(new GainEnergyAction(1));
        this.addToBot(new ApplyPowerAction(p, p, new Fluid(p, 1), 1));
        this.addToBot(new ApplyPowerAction(p, p, new Bio(p, 2), 2));
        addToTop(new DrawCardAction(AbstractDungeon.player, 1));

    }

    public void onEquip() {
            int relicAtIndex = -1;
            int relicsize=AbstractDungeon.player.relics.size();
        for (int i = 0; i < relicsize; i++) {
            if (AbstractDungeon.player.relics.get(i).relicId.equals("MuelSyseKhas:EcoSpecimen")) {
                relicAtIndex = i;
                break;
            }
        }
        if (relicAtIndex != -1) {
            AbstractDungeon.player.relics.get(relicAtIndex).onUnequip();
            AbstractRelic exuberantPlant = RelicLibrary.getRelic("MuelSyseKhas:ExuberantPlant").makeCopy();
            exuberantPlant.instantObtain(AbstractDungeon.player, relicAtIndex, false);
            logMetricRelicSwap("ExuberantPlant", "Swapped Relic", exuberantPlant, AbstractDungeon.player.relics.get(relicAtIndex));
        }         
          // 检查是否有多余的 ExuberantPlant
    int exuberantPlantCount = 0;
    int lastExuberantPlantIndex = -1;
    for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
        if (AbstractDungeon.player.relics.get(i).relicId.equals("MuelSyseKhas:ExuberantPlant")) {
            exuberantPlantCount++;
            lastExuberantPlantIndex = i;
        }
    }

    // 如果有多余的 ExuberantPlant，将最后一个替换为 Circlet
    if (exuberantPlantCount > 1 && lastExuberantPlantIndex != -1) {
        AbstractDungeon.player.relics.get(lastExuberantPlantIndex).onUnequip();
        AbstractRelic circlet = RelicLibrary.getRelic("Circlet").makeCopy();
        circlet.instantObtain(AbstractDungeon.player, lastExuberantPlantIndex, false);
        logMetricRelicSwap("ExuberantPlant", "Swapped Relic", circlet, AbstractDungeon.player.relics.get(lastExuberantPlantIndex));
    }
}  


    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic("MuelSyseKhas:EcoSpecimen");
      }

    public AbstractRelic makeCopy() {
        return new ExuberantPlant();
    }
}
