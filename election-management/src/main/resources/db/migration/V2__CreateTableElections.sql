CREATE TABLE elections (
    id VARCHAR(40) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE election_candidate (
    election_id VARCHAR(40) NOT NULL,
    candidate_id VARCHAR(40) NOT NULL,
    votes INTEGER DEFAULT 0,
    PRIMARY KEY (election_id, candidate_id)
);

-- SEED: mockaroo.com
INSERT INTO candidates (id, photo, given_name, family_name, email, phone, job_title) VALUES
    ('43da7e6b-2d2c-48ec-92bd-f33c271f8aa2', 'https://robohash.org/etfugaincidunt.png?size=50x50&set=set1', 'Allot', 'Shirley', 'sallot0@booking.com', '+351 (957) 828-5772', 'Editor'),
    ('b4335a1a-708d-4830-876a-1102a7325cc4', 'https://robohash.org/culpaiustovoluptatum.png?size=50x50&set=set1', 'Coltan', 'Stern', 'scoltan1@mlb.com', '+51 (457) 776-7022', 'Web Developer IV'),
    ('7f42cfdb-1f48-44ef-b35e-e4fa65d21b2c', 'https://robohash.org/velitvoluptatemsit.png?size=50x50&set=set1', 'Stentiford', 'Tanitansy', 'tstentiford2@umich.edu', '+63 (740) 479-7351', 'Occupational Therapist'),
    ('5c89e2f2-da40-4fc6-866b-29c37b6e9593', 'https://robohash.org/suscipitquidemdicta.png?size=50x50&set=set1', 'Tatum', 'Nadeen', 'ntatum3@istockphoto.com', '+51 (733) 683-2304', 'Help Desk Operator'),
    ('5699ed09-94c4-488e-9421-3d7a41b0887c', 'https://robohash.org/temporaautlibero.png?size=50x50&set=set1', 'Eminson', 'Mira', 'meminson4@infoseek.co.jp', '+34 (741) 226-0289', 'Electrical Engineer'),
    ('34fe22cf-4a7c-4133-ada0-147b0c65c979', 'https://robohash.org/doloremquesedrepellendus.png?size=50x50&set=set1', 'Okenfold', 'Say', 'sokenfold5@gizmodo.com', '+47 (248) 187-3105', 'Web Developer IV'),
    ('58432c21-21ad-4254-ad3e-f1c3b58f6b6f', 'https://robohash.org/quisquamvelitminima.png?size=50x50&set=set1', 'Roy', 'Jerrold', 'jroy6@live.com', '+387 (482) 787-9408', 'VP Product Management'),
    ('e0de05d9-0918-4831-afa3-148fd7ef6a24', 'https://robohash.org/rerumeligendiad.png?size=50x50&set=set1', 'Pardue', 'Corbett', 'cpardue7@behance.net', '+86 (493) 124-9670', 'Speech Pathologist'),
    ('eeb168fe-e503-46a3-b85e-7bf0d501ce46', 'https://robohash.org/quiaaliquaminventore.png?size=50x50&set=set1', 'Sherrett', 'Stanwood', 'ssherrett8@instagram.com', '+1 (682) 885-3793', 'Professor'),
    ('0f292bb0-2deb-408f-bad3-455fca79e16e', 'https://robohash.org/eligendiperferendismaxime.png?size=50x50&set=set1', 'Deans', 'Aldric', 'adeans9@istockphoto.com', '+86 (503) 681-0845', 'Marketing Manager');
