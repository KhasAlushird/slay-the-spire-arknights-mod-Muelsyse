package muelmod.actions;

import java.util.UUID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

public class AddCostAction extends AbstractGameAction {
  UUID uuid;
  
  private AbstractCard card = null;
  
  public AddCostAction(AbstractCard card) {
    this.card = card;
  }
  
  public AddCostAction(UUID targetUUID, int amount) {
    this.uuid = targetUUID;
    this.amount = amount;
    this.duration = Settings.ACTION_DUR_XFAST;
  }
  
  public void update() {
    if (this.card == null) {
      for (AbstractCard c : GetAllInBattleInstances.get(this.uuid))
        c.modifyCostForCombat(1); 
    } else {
      this.card.modifyCostForCombat(1);
    } 
    this.isDone = true;
  }
}