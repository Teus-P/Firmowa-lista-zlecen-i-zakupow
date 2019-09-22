# Firmowa-lista-zlecen-i-zakupow

Aplikacja kompatybilna z openJDK 11, openJFX 11
dodatkowe biblioteki Jfoenix

openJDK 11 https://jdk.java.net/java-se-ri/11

Konfiguracja intelliJ:

1.Import projektu:

Przy imporcie projektu wybieramy openJDK 11 dla JVM Gradle

2.Ustawienie JDK dla projektu

File -> Project Structure -> Project SDK -> wybieramy java 11
                          -> Project language level: 11

3.Uruchomienie aplikacji:

Run -> Edit Configurations -> Klikamy na górze w plusa -> Wybieramy Gradle -> W "Gradle project" wybieramy projekt do uruchomienia -> 
W "Tasks" wpisujemy: run -> klikamy OK :)

Po tych ustawieniach aplikacja odpala się za pomocą gradle i nie ma potrzeby ustawiać VM options ani dodawać javy fx z global librares do projektu.

PS: Wbudowany scenebuilder w IntelliJ'u czasami nie widzi niektórych bibliotek JFoenixa więc lepiej wszystko robić oddzielnie

******************************************************************************

RESTfull client
Integracja z api

Do konsumowania z danych od api używam biblioteki retrofit2 łatwo odbiera się dane i konwertuje z Json do Java object 
(używana jest często do androida ale do naszego klienta w fx się też dobrze nada). 
tutaj podsyłam małą ściąge do retrofita https://square.github.io/retrofit/

Klasa RestClient udostępnia dostęp do api (tam też jest też cała konfiguracja) tego w sumie póki co nie ma co ruszać bo działa.

Interfejs RestInterface tutaj dodajemy requesty do api.

Paczka model tutaj mamy zwykłe POJO które mapuje nam jsona na obiekty javy
tutaj pomocny jest generator z neta sprawdziłem i działa http://www.jsonschema2pojo.org/ generuje nam całe klasy
po prawej zmieniamy w Annotation style z Jackson na Gson !!!!!

Paczka helpers tutaj łączymy się z bazą i obieramy dane 
Póki co odbieramy wszystko synchronicznie 
przykład 

    public class UserAccountHelper {
        private RestInterface api;
        public UserAccountHelper(RestInterface api) { this.api = api; }
        
        public List<UserAccountModel> getAllUsers() throws IOException {
            Call<List<UserAccountModel>> call = api.getUsers();
            return call.execute().body();
     }}
    
wywołanie:

    UserAccountHelper userAccountHelper = new UserAccountHelper(RestClient.getApi());
    List<UserAccountModel> userAccountModelList = userAccountHelper.getAllUsers();
 
 PS: wiadomo wcześniej musimy ustawić token w klasie RestClient bo request /users jest autoryzowany
 ale to po zalogowaniu token będzie ustawiany
 
Ustawienie tokena:

    TokenHelper tokenHelper = new TokenHelper(RestClient.getApi());
    TokenModel tokenModel = tokenHelper.getToken("login", "hasło");
    RestClient.setToken(tokenModel.getToken());

PS: token ustawiany jest tylko raz

Przykład pobierania danych asynchronicznie
Musimy utworzyć interfejs który będzie przekazywał zwrócone dane 

    public interface UserAccountInterface {
        Call<List<UserAccountModel>> getUsers(OnResponseListener responseListener);
    
        interface OnResponseListener{
            void onResponseSucces(List<UserAccountModel> userAccountModelList);
            void onResponseFailture();
            void onFailtureResponse();
            void onCancelResponse();
        }
    }

Następnie w Helperze implementujemy interfejs UserAccountInterface

    public class AsyncHelper implements UserAccountInterface {
    
        private RestInterface api;
    
        public AsyncHelper(RestInterface api) {
            this.api = api;
        }
    
        @Override
        public Call<List<UserAccountModel>> getUsers(OnResponseListener responseListener) {
            Call<List<UserAccountModel>> call = api.getUsers();
    
            call.enqueue(new Callback<List<UserAccountModel>>() {
                @Override
                public void onResponse(Call<List<UserAccountModel>> call, Response<List<UserAccountModel>> response) {
                    if (response.isSuccessful()) {
                        responseListener.onResponseSucces(response.body());
                    } else {
                        responseListener.onResponseFailture();
                    }
                }
    
                @Override
                public void onFailure(Call<List<UserAccountModel>> call, Throwable throwable) {
                    responseListener.onResponseFailture();
                }
            });
    
            return call;
        }
    }

wywołanie:
klasa implementuje interfejs UserAccount.OnResponseListener

    public class Test implements UserAccountInterface.OnResponseListener {
    
        private List<UserAccountModel> userAccountModelList2;
    
    
        public void testAsync() {
            AsyncHelper asyncHelper = new AsyncHelper(RestClient.getApi());
            asyncHelper.getUsers(this);
    
        }
    
        @Override
        public void onResponseSucces(List<UserAccountModel> userAccountModelList) {
            userAccountModelList2 = userAccountModelList;
        }
    
        public List<UserAccountModel> getUserAccountModelList2() {
            return userAccountModelList2;
        }
        
        /* dalsza częśc pominięta */
    }
    
Jak odbieramy asynchronicznie dane to trzeba zrównoleglić bo wywali nullPointException !!


 
 
