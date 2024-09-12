package muelmod.actions;

//attention!! abandoned!!
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AdditionalAttackAction extends AbstractGameAction {
  int additionalAmt;
  DamageInfo.DamageType the_type;
  
  public AdditionalAttackAction(AbstractCreature target, int attack, int additional,DamageInfo.DamageType this_type) {
    this.target = target;
    this.amount = attack;
    this.additionalAmt = additional;
    the_type = this_type;
  }
  
  public void update() {
       this.addToBot(
            new DamageAction(
                this.target,
                new DamageInfo(AbstractDungeon.player, this.amount+this.additionalAmt, the_type))
        );
    addToTop((AbstractGameAction)new GainBlockAction(this.target, this.amount + this.additionalAmt));
    this.isDone = true;
  }
}
