package pl.app.api.helpers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import pl.app.api.helpers.common.CallExecutor;
import pl.app.api.interfaces.ApiResourceInterface;
import pl.app.api.model.ResponseModel;
import pl.app.api.model.UnitModel;
import pl.app.api.model.UserAccountModel;
import pl.app.api.responseInterfaces.ChangePasswordResponseListener;
import pl.app.api.responseInterfaces.EditUserResponseListener;
import pl.app.api.responseInterfaces.NewUserResponseListener;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class UserAccountHelper {

    private ApiResourceInterface apiResourceInterface;


    public UserAccountModel getMyAccountDetails() {
        Call<UserAccountModel> call = apiResourceInterface.getMyAccountDetails();

        return CallExecutor.execute(call);
    }

        public List<UserAccountModel> getAllUsers() {
            Call<List<UserAccountModel>> call = apiResourceInterface.getAllUsers();

            return CallExecutor.execute(call);
    }


    public void saveNewUserAccount(UserAccountModel userAccountModel, NewUserResponseListener listener) {
        Call<UserAccountModel> call = apiResourceInterface.saveNewUser(userAccountModel);

        Response<UserAccountModel> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                listener.onNewUserResponseSuccess(response.body());

            } else {
                Gson gson = new Gson();
                listener.onNewUserResponseFailed(gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void editUserAccountById(UserAccountModel userAccountModel, EditUserResponseListener listener) {
        Call<ResponseModel> call = apiResourceInterface.editUser(userAccountModel);

        Response<ResponseModel> response = null;

        try {
            response = call.execute();
            if (response.isSuccessful() && response.code() == 200) {
                listener.onEditUserSuccessResponse(response.body());

            } else {
                Gson gson = new Gson();
                listener.onEditUserFailedResponse(gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void changeUserAccountPassword(String oldPassword, String newPassword, ChangePasswordResponseListener listener) {
        Call<ResponseModel> call = apiResourceInterface.changeUserAccountPassword(oldPassword, newPassword);

        Response<ResponseModel> response = null;

        try {
            response = call.execute();

            if (response.isSuccessful() && response.code() == 200) {
                listener.onChangePasswordSuccessResponse(response.body());
            } else {
                Gson gson = new Gson();
                listener.onChangePasswordFailedResponse(gson.fromJson(response.errorBody() != null ? response.errorBody().string() : null, ResponseModel.class));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public UserAccountModel getAdministratorInfo() {
        Call<UserAccountModel> call = apiResourceInterface.getAdministratorHelpInfo();
        return CallExecutor.execute(call);
    }

}
