package gamblermod.cards.skills;

import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import gamblermod.actions.CostShiftAction;
import gamblermod.enumPatches.AbstractCardEnum;

public class LuckyCoin extends CustomCard implements ModalChoice.Callback
{

    public static final String ID = "gamblermod:LuckyCoin";
    private static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // Get object containing the strings that are displayed in the game.
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "gamblermod/img/cards/LuckyCoin.png";
    private static final int COST = 1;
    private static final int COST_SHIFT = 1;

    ModalChoice modal;

    public LuckyCoin()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.SKILL, AbstractCardEnum.Gambler_CHECKERBOARD,
                CardRarity.BASIC, CardTarget.SELF);
        this.baseMagicNumber=COST_SHIFT;
        this.exhaust = true;

        modal = new ModalChoiceBuilder()
                .setCallback(this)
                .setColor(AbstractCardEnum.Gambler_CHECKERBOARD)
                .addOption("Increase", "Increase the cost of the card by 1.", CardTarget.NONE)
                .addOption("Decrease", "Decrease the cost of the card by 1", CardTarget.NONE)
                .create();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if(AbstractDungeon.player.hand.size() == 0)
            return;
        modal.open();
    }

    @Override
    public void optionSelected(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster, int i)
    {
        switch(i)
        {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new CostShiftAction(-this.magicNumber));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new CostShiftAction(this.magicNumber));
                break;
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new LuckyCoin();
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
