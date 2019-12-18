package pl.app.controllers.common.listItems;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import pl.app.api.clients.ApiResourcesClient;
import pl.app.api.helpers.ImplementersHelper;
import pl.app.api.model.CategoriesModel;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.UserAccountModel;
import pl.app.api.model.UserAccountTypeModel;
import pl.app.api.responseInterfaces.ImplementerCategoriesResponseListener;

import java.util.List;

@Getter
@Setter
public class UserTableItem extends RecursiveTreeObject<UserTableItem> implements ImplementerCategoriesResponseListener {

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private ImplementersHelper implementersHelper = new ImplementersHelper(ApiResourcesClient.getApi());

    private int userId;
    private UserAccountModel userAccountModel;

    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty userLogin;
    private StringProperty email;
    private StringProperty phoneNumber;
    private StringProperty role;


    private ObservableValue<CategoriesModel> implementersCategoriesObservable;

    private ObservableValue<List<UserAccountTypeModel>> userAccountTypeModeObservable;


    public UserTableItem(UserAccountModel userAccountModel) {
        this.userAccountModel = userAccountModel;
        this.firstName = new SimpleStringProperty(userAccountModel.getFirstName());
        this.lastName = new SimpleStringProperty(userAccountModel.getLastName());
        this.userLogin = new SimpleStringProperty(userAccountModel.getUsername());
        this.email = new SimpleStringProperty(userAccountModel.getEmail());
        this.phoneNumber = new SimpleStringProperty(userAccountModel.getPhoneNumber());
        setRole();
        this.userId = userAccountModel.getIdUserAccount();
        checkIfUserIsImplementers();
    }

    private void setRole() {
        StringBuilder builder = new StringBuilder();
        userAccountModel.getUserAccountTypeModels().forEach(role -> builder.append(role.getName()).append("\n"));
        this.role = new SimpleStringProperty(builder.toString());

        this.userAccountTypeModeObservable = new ObservableValue<>() {
            @Override
            public void addListener(ChangeListener<? super List<UserAccountTypeModel>> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super List<UserAccountTypeModel>> listener) {

            }

            @Override
            public List<UserAccountTypeModel> getValue() {
                return userAccountModel.getUserAccountTypeModels();
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        };
    }

    private void checkIfUserIsImplementers() {
        if (userAccountModel.getUserAccountTypeModels() != null) {
            if (userAccountModel.getUserAccountTypeModels().stream().anyMatch(item -> item.getName().equals("Role_IMPLEMENTERS"))) {
                implementersHelper.getImplementerCategory(userAccountModel.getIdUserAccount(), this);
            }
        }

    }

    @Override
    public void onImplementerCategoriesSuccessResponse(CategoriesModel categoriesModelList) {

        this.implementersCategoriesObservable = new ObservableValue<>() {
            @Override
            public void addListener(ChangeListener<? super CategoriesModel> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super CategoriesModel> listener) {

            }

            @Override
            public CategoriesModel getValue() {
                return categoriesModelList;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        };
    }

    @Override
    public void onImplementerCategoriesFailedResponse(ResponseModel responseModel) {
        this.implementersCategoriesObservable = null;
    }
}
