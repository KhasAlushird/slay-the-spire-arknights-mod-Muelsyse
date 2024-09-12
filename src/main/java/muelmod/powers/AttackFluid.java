package muelmod.powers;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;





public class AttackFluid extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = IdHelper.makePath("AttackFluid");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AttackFluid(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的amount参数删掉，并把下面的amount改成-1就行
        this.amount = amount;

        // 添加一大一小两张能力图
        String path128 = ImageHelper.getOtherImgPath("powers", "attackfluid_84");
        String path48 = ImageHelper.getOtherImgPath("powers", "attackfluid_32");
        this.region128 = new AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount * 6); // 这样，%d就被替换成能力的层数
    }

    public void atEndOfRound() {
        flash();
        this.addToBot(new DamageAllEnemiesAction(null,DamageInfo.createDamageMatrix(this.amount * 6, true),DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
}