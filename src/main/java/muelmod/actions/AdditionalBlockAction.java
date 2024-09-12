package muelmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class AdditionalBlockAction extends AbstractGameAction {
  int additionalAmt;
  
  public AdditionalBlockAction(AbstractCreature target, int block, int additional) {
    this.target = target;
    this.amount = block;
    this.additionalAmt = additional;
  }
  
  public void update() {
    addToTop((AbstractGameAction)new GainBlockAction(this.target, this.amount + this.additionalAmt));
    this.isDone = true;
  }
}
