package muelmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardAllAction extends AbstractGameAction {

    public DiscardAllAction() {
        this.target = AbstractDungeon.player;
        this.actionType = AbstractGameAction.ActionType.DISCARD;
    }

    public void update() {
        if (this.isDone) {
            return;
        }

        for (AbstractCard card : AbstractDungeon.player.hand.group) {
                addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.hand));
        }

        this.isDone = true;
    }
}