package muelmod.actions;

import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class TurbulenceAction extends AbstractGameAction {
    private boolean upgraded = false;
    public TurbulenceAction(boolean the_upgraded) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.upgraded = the_upgraded;
    }

    @Override
    public void update() {
        boolean hasPowerCard = false;


        //注意，这里检测抽上来的卡牌，检测的是this.addToBot(new DrawCardAction(2, new TurbulenceAction(this.upgraded)));的卡牌，
        //而不是AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 2));
        Iterator var1 = DrawCardAction.drawnCards.iterator();

      while(var1.hasNext()) {
         AbstractCard c = (AbstractCard)var1.next();
         if (c.type == CardType.POWER) {
            hasPowerCard = true;
            break;
         }
      }

        if (hasPowerCard) {
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 2));
            this.addToBot(new GainEnergyAction(1));
        }

        this.isDone = true;
    }
}