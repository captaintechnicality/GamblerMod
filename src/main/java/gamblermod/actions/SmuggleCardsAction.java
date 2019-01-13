package gamblermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SmuggleCardsAction extends AbstractGameAction
{
    AbstractCreature source;
    public SmuggleCardsAction(AbstractCreature owner)
    {
        source = owner;
    }

    @Override
    public void update()
    {
        if(AbstractDungeon.player.drawPile.size() > 0) {
            AbstractCard topCard = AbstractDungeon.player.drawPile.getTopCard();
            AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(topCard, AbstractDungeon.player.drawPile));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(source, topCard.cost));
        }
        isDone = true;
    }
}
