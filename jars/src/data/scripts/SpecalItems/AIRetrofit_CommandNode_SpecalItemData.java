package data.scripts.SpecalItems;

import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.characters.PersonAPI;

public class AIRetrofit_CommandNode_SpecalItemData extends SpecialItemData {
    PersonAPI person = null;
    public AIRetrofit_CommandNode_SpecalItemData(String id, String data,PersonAPI person) {
        super(id, data);
        this.person = person;
    }
    public PersonAPI getPerson(){
        return person;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = super.hashCode();
        result = prime * result + ((person == null) ? 0 : person.hashCode());
        return result;
    }

    @Override
    public void setData(String data) {
        super.setData(data);
    }

    @Override
    public String getData() {
        return super.getData();
    }
}
