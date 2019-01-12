package gamblermod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javafx.util.Pair;

import java.util.ArrayList;

public class AceHeartsAction extends AbstractGameAction
{

    AbstractCreature source;
    int drawMul;
    AbstractPower power;

    public AceHeartsAction(AbstractCreature _source, int stacks, AbstractPower _power)
    {
        source = _source;
        drawMul = stacks;
        power = _power;
    }

    @Override
    public void update()
    {
        AbstractPlayer player = AbstractDungeon.player;
        CardGroup hand = player.hand;
        ArrayList<Pair<PokerHandCheck.Suit, Integer>> pokerHand = new ArrayList<>();
        int powers = 0;
        for(AbstractCard card : hand.group)
        {
            if(card.type == AbstractCard.CardType.POWER)
                powers++;
        }

        if(powers > 0) {
            power.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(source, powers * drawMul));
        }

        this.isDone = true;
    }
}
