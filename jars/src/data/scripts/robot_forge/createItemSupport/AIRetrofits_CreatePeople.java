package data.scripts.robot_forge.createItemSupport;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import data.scripts.AIWorldCode.Fleet.setDataLists;

import java.util.List;
import java.util.Random;

public class AIRetrofits_CreatePeople {
    public static PersonAPI createAdmen(){
        PersonAPI person = OfficerManagerEvent.createAdmin(Global.getSector().getPlayerFaction(),0,new Random());
        setPerson(person);
        return person;
    }
    private static final String[] personalities = {"timid","cautious","steady","aggressive","reckless","fearless"};
    public static PersonAPI createOfficer(int power){
        PersonAPI person = OfficerManagerEvent.createOfficer(Global.getSector().getPlayerFaction(),1);
        person.setPersonality(personalities[power]);
        setPerson(person);
        //possibility to add he possibility of having an elite skill to start?
        List<MutableCharacterStatsAPI.SkillLevelAPI> skillsCopy = person.getStats().getSkillsCopy();
        for(int a2 = 0; a2 < skillsCopy.size(); a2++) {
            MutableCharacterStatsAPI.SkillLevelAPI a = skillsCopy.get(a2);
            if (a.getSkill().isElite()) {
                person.getStats().decreaseSkill(a.getSkill().getId());
            }
        }
        return person;
    }
    private static void setPerson(PersonAPI person){
        //PersonAPI person = Global.getFactory().createPerson();
        person.setPortraitSprite(setDataLists.getRandom(2));
        person.setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
    }


    public static PersonAPI createAdmen(float skillPower){
        /*skillPower represents the possibility to add more skills to this caption on creation. */
        PersonAPI person = createAdmen();

        return person;
    }
}
