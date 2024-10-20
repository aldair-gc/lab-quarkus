package infrastructure.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="candidates")
public class Candidate {
    @Id
    private String id;
    private String photo;
    @Column(name="given_name")
    private String givenName;
    @Column(name="family_name")
    private String familyName;
    private String email;
    private String phone;
    @Column(name="job_title")
    private String jobTitle;

    
}
