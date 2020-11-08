package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.ReadOnlyTbmManager;
import seedu.address.model.TbmManager;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.ContractExpiryDate;
import seedu.address.model.client.Email;
import seedu.address.model.client.LastModifiedInstant;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Timezone;
import seedu.address.model.country.Country;
import seedu.address.model.note.CountryNote;
import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code TbmManager} with sample data.
 */
public class SampleDataUtil {

    /**
     * Returns a standard list of sample client data.
     */
    public static Client[] getSampleClients() {
        Client alex = new Client(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Country("SG"), new Timezone("UTC+08:00"),
                new ContractExpiryDate("21-4-2022"), new LastModifiedInstant("2020-01-01T00:00:00.000000Z"));
        Client bernice = new Client(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Country("SG"),
                new Timezone("UTC+08:00"), new ContractExpiryDate("12-12-2021"),
                new LastModifiedInstant("2020-02-02T00:00:00.000000Z"));
        Client charlotte = new Client(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"), new Address("Calle de Miguel Angel, 3, 28010 Madrid"),
                new Country("ES"), new Timezone("UTC+01:00"), new ContractExpiryDate("1-4-2023"),
                new LastModifiedInstant("2020-03-03T00:00:00.000000Z"));
        Client david = new Client(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Country("SG"),
                new Timezone("UTC+08:00"), new ContractExpiryDate("23-12-2020"),
                new LastModifiedInstant("2020-04-04T00:00:00.000000Z"));
        Client irfan = new Client(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Country("SG"), new Timezone("UTC+08:00"),
                new ContractExpiryDate("3-2-2021"), new LastModifiedInstant("2020-05-05T00:00:00.000000Z"));
        Client roy = new Client(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Dhaka, Aleya Design, Lane "), new Country("BD"), new Timezone("UTC+06:00"),
                new ContractExpiryDate("9-6-2024"), new LastModifiedInstant("2020-06-06T00:00:00.000000Z"));

        Set<Tag> charlotteNote1Tags = new HashSet<>();
        charlotteNote1Tags.add(new Tag("socialising"));
        Note charlotteNote1 = new Note("Networking event at Ibiza");
        charlotteNote1.setTags(charlotteNote1Tags);
        charlotte.addClientNote(charlotteNote1);

        Set<Tag> berniceNote1Tags = new HashSet<>();
        berniceNote1Tags.add(new Tag("reminder"));
        Note berniceNote1 = new Note("Birthday tomorrow.");
        berniceNote1.setTags(berniceNote1Tags);
        bernice.addClientNote(berniceNote1);

        Set<Tag> alexNote1Tags = new HashSet<>();
        alexNote1Tags.add(new Tag("holidayPlans"));
        alexNote1Tags.add(new Tag("romantic"));
        Note alexNote1 = new Note("Wants to take his wife to Norway for New Years' Day");
        alexNote1.setTags(alexNote1Tags);

        Set<Tag> alexNote2Tags = new HashSet<>();
        alexNote2Tags.add(new Tag("altruist"));
        alexNote2Tags.add(new Tag("socialEnterprise"));
        Note alexNote2 = new Note("Likes doing volunteer work");
        alexNote2.setTags(alexNote2Tags);

        Set<Tag> alexNote3Tags = new HashSet<>();
        alexNote3Tags.add(new Tag("meetings"));
        alexNote3Tags.add(new Tag("workPreferences"));
        Note alexNote3 = new Note("Prefers to meet up at his office, not that much of a public spaces guy");
        alexNote3.setTags(alexNote3Tags);

        Set<Tag> alexNote4Tags = new HashSet<>();
        alexNote4Tags.add(new Tag("hobbies"));
        Note alexNote4 = new Note("Need to meet him to see his vintage Honda with a side-car."
                + "He really loves old cars");
        alexNote4.setTags(alexNote4Tags);

        Set<Tag> alexNote5Tags = new HashSet<>();
        alexNote5Tags.add(new Tag("reminder"));
        Note alexNote5 = new Note("Birthday tomorrow");
        alexNote5.setTags(alexNote5Tags);

        alex.addClientNote(alexNote1);
        alex.addClientNote(alexNote2);
        alex.addClientNote(alexNote3);
        alex.addClientNote(alexNote4);
        alex.addClientNote(alexNote5);
        return new Client[] { alex, bernice, charlotte, david, irfan, roy };
    }

    /**
     * Returns a standard list of sample country note data.
     */
    public static List<CountryNote> getSampleCountryNotes() {
        CountryNote firstCountryNote = new CountryNote("Small domestic market as compared "
                + "to rest of SEA countries", new Country("SG"));
        Set<Tag> firstCountryNoteTags = new HashSet<>();
        firstCountryNoteTags.add(new Tag("marketsize"));
        firstCountryNote.setTags(firstCountryNoteTags);

        CountryNote secondCountryNote = new CountryNote("Political unrest in the capital",
                new Country("TH"));
        Set<Tag> secondCountryNoteTags = new HashSet<>();
        secondCountryNoteTags.add(new Tag("politics"));
        secondCountryNote.setTags(secondCountryNoteTags);

        CountryNote thirdCountryNote = new CountryNote("A high-context country, hence building "
                + "relationships is important for doing business", new Country("JP"));
        Set<Tag> thirdCountryNoteTags = new HashSet<>();
        thirdCountryNoteTags.add(new Tag("intercultural"));
        thirdCountryNote.setTags(thirdCountryNoteTags);

        return Arrays.asList(firstCountryNote, secondCountryNote, thirdCountryNote);
    }

    /**
     * Returns TBM Manager with sample data.
     */
    public static ReadOnlyTbmManager getSampleTbmManager() {
        TbmManager sampleTm = new TbmManager();
        for (Client sampleClient : getSampleClients()) {
            sampleTm.addClient(sampleClient);
        }

        for (CountryNote countryNote : getSampleCountryNotes()) {
            sampleTm.addCountryNote(countryNote);
        }

        return sampleTm;
    }
}
