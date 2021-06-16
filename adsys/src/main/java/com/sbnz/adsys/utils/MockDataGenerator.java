package com.sbnz.adsys.utils;

import com.github.javafaker.Faker;
import com.sbnz.adsys.model.*;
import com.sbnz.adsys.repository.*;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.*;

public class MockDataGenerator {

    private final ApplicationContext context;

    private final Faker faker;
    private final Random random;

    private static final int ADVERTISER_COUNT = 5;
    private static final int ADVERTISEMENT_COUNT = 100;
    private static final int PAGES_COUNT = 25;
    private static final int USER_COUNT = 50;

    private static final int MIN_TAGS_PER_AD = 1;
    private static final int MAX_TAGS_PER_AD = 3;

    private static final int MIN_PAGES_LIKED = 3;
    private static final int MAX_PAGES_LIKED = 8;

    private final Map<String, Authority> authorityMap = new HashMap<>();

    private final List<Tag> tags = new LinkedList<>();
    private final List<Advertiser> advertisers = new LinkedList<>();
    private final List<Advertisement> advertisements = new LinkedList<>();
    private final List<SocialMediaPage> pages = new LinkedList<>();
    private final List<User> users = new LinkedList<>();
    private final List<SocialMediaUser> socialMediaUsers = new LinkedList<>();

    public MockDataGenerator(ApplicationContext context) {
        this.context = context;
        this.faker = new Faker();
        this.random = new Random();
    }

    public void test() {
        System.out.println(faker.avatar().image());
        System.out.println(faker.internet().image());
    }

    public void generateData() {
        System.out.println("Purging database...");
        purgeDatabase();

        generateAuthorities();
        generateTags();

        System.out.println("Generating advertisers...");
        generateAdvertisers();

        System.out.println("Generating advertisements now...");
        generateAdvertisements();

        System.out.println("Generating social media pages...");
        generatePages();

        System.out.println("Generating users...");
        generateUsers();

        System.out.println("Now social media users...");
        generateSocialMediaUsers();

        System.out.println("FINISHED!");
    }

    private void generateAuthorities() {
        AuthorityRepository repository = context.getBean(AuthorityRepository.class);
        Authority userAuthority = Authority.builder().name("USER").build();
        authorityMap.put("user", repository.save(userAuthority));

        Authority admin = Authority.builder().name("ADMIN").build();
        authorityMap.put("admin", repository.save(admin));
    }

    private void generateTags() {
        TagRepository repository = context.getBean(TagRepository.class);
        List<String> keyWords = Arrays.asList("sport", "football", "basketball", "tennis", "retail", "technology",
                "beauty", "news", "internet", "video game", "movie", "tv show");

        keyWords.forEach(word -> tags.add(repository.save(new Tag(word))));
    }

    private void generateAdvertisers() {
        AdvertiserRepository repository = context.getBean(AdvertiserRepository.class);

        for (int i = 0; i < ADVERTISER_COUNT; i++) {
            while (true) {
                try {
                    Advertiser advertiser = Advertiser.builder()
                            .name(faker.company().name())
                            .picture(faker.avatar().image())
                            .build();
                    advertisers.add(repository.save(advertiser));
                    break;
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void generateAdvertisements() {
        AdvertisementRepository repository = context.getBean(AdvertisementRepository.class);

        for (int i = 0; i < ADVERTISEMENT_COUNT; i++) {
            while (true) {
                try {
                    Advertisement ad = Advertisement.builder()
                            .advertiser(getRandomElement(advertisers))
                            .title(faker.company().catchPhrase())
                            .content(faker.howIMetYourMother().quote() + ".\n" + faker.rickAndMorty().quote() + ".\n"
                                    + faker.gameOfThrones().quote())
                            .datePublished(LocalDate.now().minusDays(random.nextInt(90)))
                            .targetUrl(faker.company().url())
                            .tags(getRandomElements(tags, MIN_TAGS_PER_AD, MAX_TAGS_PER_AD))
                            .build();
                    advertisements.add(repository.save(ad));
                    break;
                } catch (Exception ignored) {
                }
            }
        }

    }

    private void generatePages() {
        SocialMediaPageRepository repository = context.getBean(SocialMediaPageRepository.class);

        for (int i = 0; i < PAGES_COUNT; i++) {
            while (true) {
                try {
                    SocialMediaPage page = SocialMediaPage.builder()
                            .name(faker.funnyName().name())
                            .categoryKeywords(getRandomElements(tags, MIN_TAGS_PER_AD, MAX_TAGS_PER_AD))
                            .usersWhoLikeThePage(new LinkedList<>())
                            .build();
                    pages.add(repository.save(page));
                    break;
                } catch (Exception ignored) {
                }
            }
        }
    }

    private void generateUsers() {
        UserRepository repository = context.getBean(UserRepository.class);

        // generate one admin
        List<Authority> adminAuthority = new LinkedList<>();
        adminAuthority.add(authorityMap.get("admin"));
        adminAuthority.add(authorityMap.get("user"));
        User admin = User.builder()
                .firstName("admin")
                .lastName("admin")
                .email("admin_1")
                .password("123")
                .authorities(adminAuthority)
                .enabled(true)
                .build();
        users.add(repository.save(admin));

        for (int i = 1; i < USER_COUNT + 1; i++) {
            while (true) {
                List<Authority> authorities = new LinkedList<>();
                authorities.add(authorityMap.get("user"));
                try {
                    User user = User.builder()
                            .firstName(faker.name().firstName())
                            .lastName(faker.name().lastName())
                            .email("email_" + i)
                            .password("123")
                            .authorities(authorities)
                            .enabled(true)
                            .build();
                    users.add(repository.save(user));
                    break;
                } catch (Exception ignored) {
                    System.out.println(ignored.getMessage());
                }
            }
        }
    }

    private void generateSocialMediaUsers() {
        SocialMediaUserRepository repository = context.getBean(SocialMediaUserRepository.class);
        SocialMediaPageRepository pageRepository = context.getBean(SocialMediaPageRepository.class);

        for (User user : users) {
            List<SocialMediaPage> pagesToLike = getRandomElements(pages, MIN_PAGES_LIKED, MAX_PAGES_LIKED);
            List<Advertisement> allAdsShuffled = getRandomElements(advertisements, advertisements.size());

            List<Advertisement> ignored = new LinkedList<>();
            List<Advertisement> clicked = new LinkedList<>();

            // 33% chance to be ignored clicked or not shown at all
            for (int i = 0; i < allAdsShuffled.size(); i++) {
                int randomNum = random.nextInt(3);
                if (randomNum == 0)
                    ignored.add(allAdsShuffled.get(i));
                else if (randomNum == 1)
                    clicked.add(allAdsShuffled.get(i));
            }

            SocialMediaUser socialMediaUser = SocialMediaUser.builder()
                    .user(user)
                    .age(18 + random.nextInt(70 - 18))
                    .country("Serbia")
                    .city("Stara Pazova")
                    .likedSocialMediaPages(pagesToLike)
                    .advertisementsToBeShown(new LinkedList<>())
                    .seenAdvertisements(new LinkedList<>())
                    .ignoredAdvertisements(ignored)
                    .clickedAdvertisements(clicked)
                    .build();

            SocialMediaUser savedUser = repository.save(socialMediaUser);
            socialMediaUsers.add(savedUser);

            pagesToLike.forEach(page -> {
                page.getUsersWhoLikeThePage().add(savedUser);
                pageRepository.save(page);
            });
        }
    }

    private void purgeDatabase() {
        Class[] classes = {AdvertiserRepository.class, AdvertisementRepository.class, TagRepository.class,
                AdvertisementRequestRepository.class, EventRepository.class, SocialMediaPageRepository.class,
                SocialMediaUserRepository.class, UserRepository.class};

        for (Class oneClass : classes) {
            JpaRepository<Object, Long> repo = (JpaRepository<Object, Long>) context.getBean(oneClass);
            repo.deleteAll();
        }
    }


    private <T> T getRandomElement(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    private <T> List<T> getRandomElements(List<T> list, int numElements) {
        Collections.shuffle(list);

        List<T> newList = new LinkedList<>();
        for (int i = 0; i < numElements; i++)
            newList.add(list.get(i));

        return newList;
    }

    private <T> List<T> getRandomElements(List<T> list, int min, int max) {
        int numElements = min + random.nextInt(max - min);
        Collections.shuffle(list);

        List<T> newList = new LinkedList<>();
        for (int i = 0; i < numElements; i++)
            newList.add(list.get(i));

        return newList;
    }
}
