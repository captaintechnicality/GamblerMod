package gamblermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
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
        if(AbstractDungeon.player.drawPile.isEmpty())
        {
            if(!AbstractDungeon.player.discardPile.isEmpty())
            {
                AbstractDungeon.actionManager.addToTop(new SmuggleCardsAction(source));
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.4f));
                AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
            }
            this.isDone = true;
            return;
        }
        else
        {
            AbstractCard topCard = AbstractDungeon.player.drawPile.getTopCard();
            AbstractDungeon.player.drawPile.group.remove(topCard);
            AbstractDungeon.player.limbo.group.add(topCard);
            topCard.target_x = (float) Settings.WIDTH / 2.0F;
            topCard.target_y = (float)Settings.HEIGHT / 2.0F;
            topCard.targetAngle = 0.0F;
            topCard.drawScale = 0.12F;
            topCard.targetDrawScale = 0.75F;
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4f));
            AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(topCard, AbstractDungeon.player.limbo));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(source, topCard.cost));
        }
        isDone = true;
    }
}
