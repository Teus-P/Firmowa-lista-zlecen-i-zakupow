
    create table categories (
       id integer not null auto_increment,
        name varchar(200) not null,
        primary key (id)
    ) engine=InnoDB;

    create table implementers (
       id integer not null auto_increment,
        idCategories integer not null,
        idUserAccount integer not null,
        primary key (id)
    ) engine=InnoDB;

    create table orderProduct (
       id integer not null auto_increment,
        inStock integer not null,
        issuedQuantity integer not null,
        quantity integer not null,
        idShoppingList integer not null,
        idOrder integer not null,
        idProduct integer not null,
        primary key (id)
    ) engine=InnoDB;

    create table orderStatus (
       id integer not null auto_increment,
        description varchar(255) not null,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table product (
       id integer not null auto_increment,
        deleted bit,
        name varchar(200) not null,
        quantity integer not null,
        idCategories integer not null,
        idUnit integer not null,
        primary key (id)
    ) engine=InnoDB;

    create table productProposal (
       id integer not null auto_increment,
        acceptedStatus bit not null,
        description varchar(255) not null,
        name varchar(200) not null,
        idCategories integer,
        idUserAccount integer,
        primary key (id)
    ) engine=InnoDB;

    create table shoppingList (
       id integer not null auto_increment,
        beginDate datetime(6),
        endDate datetime(6),
        readyStatus bit not null,
        idImplementers integer not null,
        idShoppingListStatus integer not null,
        primary key (id)
    ) engine=InnoDB;

    create table shoppingListStatus (
       id integer not null auto_increment,
        description varchar(255) not null,
        name varchar(50) not null,
        primary key (id)
    ) engine=InnoDB;

    create table systemConfiguration (
       id integer not null auto_increment,
        beginningTimeToGiveOrder time not null,
        endingTimeToGiveOrder time not null,
        timeToPickUpOrder time not null,
        idUserAccount integer not null,
        primary key (id)
    ) engine=InnoDB;

    create table unit (
       id integer not null auto_increment,
        unit varchar(20) not null,
        primary key (id)
    ) engine=InnoDB;

    create table userAccount (
       id integer not null auto_increment,
        email varchar(150) not null,
        firstName varchar(30) not null,
        lastName varchar(50) not null,
        login varchar(25) not null,
        password varchar(64) not null,
        pesel bigint not null,
        phoneNumber varchar(13) not null,
        primary key (id)
    ) engine=InnoDB;

    create table userAccountType (
       id integer not null auto_increment,
        description varchar(255) not null,
        name varchar(200) not null,
        primary key (id)
    ) engine=InnoDB;

    create table userOrder (
       id integer not null auto_increment,
        date datetime(6) not null,
        userInfo bit not null,
        idOrderStatus integer not null,
        idRecipient integer,
        idStoreKeeper integer,
        idUserAccount integer not null,
        primary key (id)
    ) engine=InnoDB;

    alter table categories 
       add constraint UK_t8o6pivur7nn124jehx7cygw5 unique (name);

    alter table orderProduct 
       add constraint UK_5vog03kro0dfvoe7b8cmuejis unique (idOrder);

    alter table orderProduct 
       add constraint UK_bgrg2rpoylwrldudd729g5mos unique (idProduct);

    alter table orderStatus 
       add constraint UK_s0hbm87hswgd3xr8992veoci5 unique (name);

    alter table product 
       add constraint UK_jmivyxk9rmgysrmsqw15lqr5b unique (name);

    alter table shoppingListStatus 
       add constraint UK_ljyh8c0mgo1xruamm3utq0ymy unique (name);

    alter table unit 
       add constraint UK_bg019o25br570qfwc0nx9bhx3 unique (unit);

    alter table userAccount 
       add constraint UK974e4xdk7hg1b87dnrw2byu92 unique (login, pesel);

    alter table userAccount 
       add constraint UK_iytf5trq01qprbdnf9khmbx4o unique (login);

    alter table userAccount 
       add constraint UK_9a3x56fc8on6664ucq4rc13ob unique (pesel);

    create table UserAccountTypeCollection (
       idUserAccount integer not null,
        idUserAccountType integer not null,
        primary key (idUserAccount, idUserAccountType)
    ) engine=InnoDB;

    alter table implementers 
       add constraint FK8a5dgad1pds8n9dqsjs7p3iv 
       foreign key (idCategories) 
       references categories (id);

    alter table implementers 
       add constraint FKtden344kt00bthvx6p3eh3cmu 
       foreign key (idUserAccount) 
       references userAccount (id);

    alter table orderProduct 
       add constraint FK21qcxcmrv5el06ysbhsmlv7eh 
       foreign key (idShoppingList) 
       references shoppingList (id);

    alter table orderProduct 
       add constraint FK1cktjmmng3m36ipk9y80qw1o5 
       foreign key (idOrder) 
       references userOrder (id);

    alter table orderProduct 
       add constraint FKb8g8cp9c4q8xr9apycv57khqm 
       foreign key (idProduct) 
       references product (id);

    alter table product 
       add constraint FKfj2v2uppw4bsc1xqy49qlsrc0 
       foreign key (idCategories) 
       references categories (id);

    alter table product 
       add constraint FKcetcxcqmtwhtlksbuyq1aksy 
       foreign key (idUnit) 
       references unit (id);

    alter table productProposal 
       add constraint FK69958tlq24d4pq3gkrjgspt9t 
       foreign key (idCategories) 
       references categories (id);

    alter table productProposal 
       add constraint FKmnatbu64xx0fb5niy3qm1u51m 
       foreign key (idUserAccount) 
       references userAccount (id);

    alter table shoppingList 
       add constraint FKjy0owoqwt1rfi8y2xmik2rwg2 
       foreign key (idImplementers) 
       references implementers (id);

    alter table shoppingList 
       add constraint FKtmx8p3s1ruuid3nea66k7rp67 
       foreign key (idShoppingListStatus) 
       references shoppingListStatus (id);

    alter table systemConfiguration 
       add constraint FKflc14m05ahxxani26fjad1qf5 
       foreign key (idUserAccount) 
       references userAccount (id);

    alter table userOrder 
       add constraint FK5fd0t5ol6ncopjdgu0gania36 
       foreign key (idOrderStatus) 
       references orderStatus (id);

    alter table userOrder 
       add constraint FKeammx9je4viws4jlb7n5tjg33 
       foreign key (idRecipient) 
       references userAccount (id);

    alter table userOrder 
       add constraint FKgb16octoy3vvswasuaywuo2ng 
       foreign key (idStoreKeeper) 
       references userAccount (id);

    alter table userOrder 
       add constraint FKhctpv6x8gxqr17y08lbrpc9hl 
       foreign key (idUserAccount) 
       references userAccount (id);

    alter table UserAccountTypeCollection 
       add constraint FKcsx1i58g8na2na3qev589asy4 
       foreign key (idUserAccountType) 
       references userAccountType (id);

    alter table UserAccountTypeCollection 
       add constraint FKr6tmdkqlpmb5v5cyxj9gagapr 
       foreign key (idUserAccount) 
       references userAccount (id);
