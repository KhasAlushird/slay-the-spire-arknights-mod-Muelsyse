package muelmod.powers;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import muelmod.helpers.IdHelper;
import muelmod.helpers.ImageHelper;



public class RandomBuff extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = IdHelper.makePath("RandomBuff");
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;



    public RandomBuff(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;


        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
        this.amount = Amount;

        // 添加一大一小两张能力图
        String path128 = ImageHelper.getOtherImgPath("powers","randombuff_84");
        String path48 = ImageHelper.getOtherImgPath("powers","randombuff_32");
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
        this.description = String.format(DESCRIPTIONS[0], this.amount); // 这样，%d就被替换成能力的层数
    }

    public void atEndOfRound() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
         // 创建一个随机数生成器
         Random random = new Random();

         // 生成一个0到99的随机数
         int randomNumber = random.nextInt(100);
 
         // 根据随机数确定触发哪个事件
         if (randomNumber < 10) {
            this.addToBot(new ApplyPowerAction(p, p, new Fluid(p, this.amount), this.amount));
         } else if (randomNumber < 35) {
            this.addToBot(new ApplyPowerAction(p, p, new AttackFluid(p, this.amount), this.amount));
         } else if (randomNumber < 60) {
            this.addToBot(new ApplyPowerAction(p, p, new BlockFluid(p, this.amount), this.amount));
         } else if (randomNumber < 70) {
            this.addToBot(new ApplyPowerAction(p, p, new FluidAdd(p, this.amount), this.amount));
         } else if (randomNumber < 85) {
            this.addToBot(new ApplyPowerAction(p, p, new EnergyFluid(p, this.amount), this.amount));}
         else {
            this.addToBot(new ApplyPowerAction(p, p, new Bio(p, this.amount), this.amount));
         }
      }

    
   
}
