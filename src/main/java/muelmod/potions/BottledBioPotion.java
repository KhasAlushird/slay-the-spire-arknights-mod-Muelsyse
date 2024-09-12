package muelmod.potions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import muelmod.helpers.IdHelper;
import muelmod.powers.Bio;

public class BottledBioPotion extends AbstractPotion {
  public static final String POTION_ID = IdHelper.makePath("BottledBioPotion");
  
  private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString( IdHelper.makePath("BottledBioPotion"));
  
  public BottledBioPotion() {
    super(potionStrings.NAME,  IdHelper.makePath("BottledBioPotion") , AbstractPotion.PotionRarity.COMMON, AbstractPotion.PotionSize.BOTTLE, AbstractPotion.PotionColor.GREEN);
    this.isThrown = false;
  }
  
  public void initializeData() {
    this.potency = getPotency();
    this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
    this.tips.clear();
    this.tips.add(new PowerTip(this.name, this.description));
    this.tips.add(new PowerTip(
          
          TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]), (String)GameDictionary.keywords
          .get(GameDictionary.BLOCK.NAMES[0])));
  }
  
  public void use(AbstractCreature target) {
     this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Bio(AbstractDungeon.player, this.potency), this.potency));
     this.addToTop(new DrawCardAction(AbstractDungeon.player, 2));
  }
  
  public int getPotency(int ascensionLevel) {
    return 1;
  }
  
  public AbstractPotion makeCopy() {
    return new BottledBioPotion();
  }
}
