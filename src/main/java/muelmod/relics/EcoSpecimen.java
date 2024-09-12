package muelmod.relics;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
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
public class EcoSpecimen extends CustomRelic {
    // 遗物ID
    public static final String ID = IdHelper.makePath("EcoSpecimen");
    // 图片路径
    private static final String IMG_PATH =ImageHelper.getOtherImgPath("relics", "ecospecimen");
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public EcoSpecimen() {
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

    }

    public AbstractRelic makeCopy() {
        return new EcoSpecimen();
    }
}
