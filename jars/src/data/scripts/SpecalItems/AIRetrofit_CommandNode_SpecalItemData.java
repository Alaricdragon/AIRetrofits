package data.scripts.SpecalItems;

import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.characters.PersonAPI;

public class AIRetrofit_CommandNode_SpecalItemData extends SpecialItemData {
    PersonAPI person = null;
    String type = "";
    public AIRetrofit_CommandNode_SpecalItemData(String id, String data,PersonAPI person) {
        super(id, data);
        this.person = person;
    }
    public AIRetrofit_CommandNode_SpecalItemData(String id, String data) {
        super(id, data);
        //AIRetrofits_CreatePeople.createPerson();
    }
    public PersonAPI getPerson(){
        return person;
    }
    public String getPersonType(){
        return type;
    }
    public void setPerson(PersonAPI person){
        this.person = person;
    }
    public void setPersonType(String type){
        this.type = type;
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
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        AIRetrofit_CommandNode_SpecalItemData other = (AIRetrofit_CommandNode_SpecalItemData) obj;
        if (getData() == null) {
            if (other.getData() != null)
                return false;
        } else if (!getData().equals(other.getData()))
            return false;

        if (getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!getId().equals(other.getId()))
            return false;

        if (person == null) {
            return other.person == null;
        } else return person.equals(other.person);

    }
    //String personType = "";

}
