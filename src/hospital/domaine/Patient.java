package hospital.domaine;


import hospital.exception.IllegalReportException;
import hospital.factory.TrackingCardFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ferlicotdelbe on 23/10/15.
 */
public class Patient {

    protected String lastName;
    protected Long numSS;
    protected String address;
    protected Integer age;
    protected Map<Speciality, TrackingCard> trackingCards;
    protected StayCard stayCard;

    public Patient(String lastName, Long numSS, String address, Integer age) {
        this.lastName = lastName;
        this.numSS = numSS;
        this.address = address;
        this.age = age;
        this.trackingCards = new HashMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;

        Patient patient = (Patient) o;

        return !(lastName != null ? !lastName.equals(patient.lastName) : patient.lastName != null) && !(numSS != null ? !numSS.equals(patient.numSS) : patient.numSS != null) && !(address != null ? !address.equals(patient.address) : patient.address != null) && !(age != null ? !age.equals(patient.age) : patient.age != null);

    }

    @Override
    public int hashCode() {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + (numSS != null ? numSS.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }

    public String lastName() {
        return lastName;
    }

    public StayCard getStayCard() {
        return stayCard;
    }

    /**
     * Destroy the "stayingCard" of the Patient = set the "stayingCard"  to null
     */
    public void destroyStayingCard() {
        //TODO Export the reports of the StayCard into the TrackingCard
        stayCard = null;
    }

    public void printStayingCard() {
        //TODO I print the staying card of the patient and the tracking cards.
        //TODO�If the patient don't have one I could maybe use a Null Object Pattern :)
        //TODO�maybe use printTrackingCardOf
    }

    /**
     * @param speciality the Speciality to find in the StayCard of the Patient
     * @return true if the Speciality is needed, else false
     */
    public boolean needSpeciality(Speciality speciality) {
        return stayCard.specialityReportMap.containsKey(speciality);
    }

    /**
     * Print all the Reports of a TrackingCard
     *
     * @param speciality
     */
    public void printTrackingCardOf(Speciality speciality) {
        if (this.trackingCards.isEmpty() || this.trackingCards.get(speciality).getReports().isEmpty()) {
            System.out.println(lastName + " n'a aucun compte rendu pour la spécialité " + speciality + ".");
        } else {
            Set<Report> reports = trackingCards.get(speciality).getReports();
            for (Report r : reports) {
                System.out.println("--------------------");
                System.out.println("COMPTE RENDU DU " + r.getDate());
                System.out.println("RAPPORT: " + r.getReport());
                System.out.println("END OF THE REPORT");
            }
        }
    }

    /**
     * TODO
     *
     * @return
     */
    public boolean isAtTheHospital() {
        return this.stayCard != null;
    }

    public void stayCard(StayCard stayCard) {
        this.stayCard = stayCard;
    }

    /**
     * TODO
     * @param speciality
     * @param report
     */
    public void addReportFor(Speciality speciality, Report report) throws IllegalReportException {
        this.stayCard.addReportToSpeciality(speciality, report);
        this.trackingCards.get(speciality).addReport(report);
    }

    public void needConsultationFor(Speciality speciality) {
        this.trackingCards.putIfAbsent(speciality, TrackingCardFactory.current().newTrackingCard());
    }
}
