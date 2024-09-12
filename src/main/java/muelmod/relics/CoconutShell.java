package muelmod.relics;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;

public class CoconutShell extends CustomRelic {
    // 遗物ID
    public static final String ID = IdHelper.makePath("CoconutShell");
    // 图片路径
    private static final String IMG_PATH = ImageHelper.getOtherImgPath("relics", "coconutshell");
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.RARE;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.SOLID;

    public CoconutShell() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractPower bioPower = AbstractDungeon.player.getPower("MuelSyseKhas:Bio");
        if (bioPower != null && bioPower.amount > 0) {
            flash();
            addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, bioPower.amount * 4));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public AbstractRelic makeCopy() {
        return new CoconutShell();
    }
}