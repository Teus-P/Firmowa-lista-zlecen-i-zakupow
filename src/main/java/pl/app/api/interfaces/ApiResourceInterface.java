package pl.app.api.interfaces;

import pl.app.api.model.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ApiResourceInterface {

    @POST("/user")
    Call<UserAccountModel> saveNewUser(
            @Body UserAccountModel userAccountModel
    );

    @GET("/categories")
    Call<List<CategoriesModel>> getAllCategories();

    @POST("/categories")
    Call<ResponseModel> createNewCategory(
            @Body CategoriesModel categoriesModel
    );

    @PUT("/categories/edit/{id}")
    Call<ResponseModel> editCategoryById(
            @Path("id") int id,
            @Body CategoriesModel categoriesModel
    );

    @PUT("/categories/delete/{id}")
    Call<ResponseModel> deleteCategoryById(
            @Path("id") int id
    );

    @GET("/unit")
    Call<List<UnitModel>> getAllUnits();

    @POST("/unit")
    Call<ResponseModel> createNewUnit(
            @Body UnitModel unitModel
    );

    @PUT("/unit/edit/{id}")
    Call<ResponseModel> editUnitById(
            @Path("id") int id,
            @Body UnitModel unitModel
    );

    @PUT("/unit/delete/{id}")
    Call<ResponseModel> deleteUnitById(
            @Path("id") int id
    );

    @GET("/order/{id}")
    Call<OrderModel> getOrderById(
            @Path("id") int id
    );

    @GET("/order/all")
    Call<List<OrderModel>> getAllOrders();

    @POST("/order/create")
    Call<ResponseModel> createNewOrder(
            @Body List<OrderProductModel> orderProductList
    );

    @POST("/product")
    Call<ResponseModel> createNewProduct(
            @Body ProductModel productModel
    );

    @GET("/product")
    Call<List<ProductModel>> getAllProducts();

    @GET("/user/all")
    Call<List<UserAccountModel>> getAllUsers();

    @GET("/useraccounttype/extra")
    Call<List<UserAccountTypeModel>> getExtraAccountType();

    @PUT("/product/delete/{id}")
    Call<ResponseModel> deleteProductById(
            @Path("id") int id
    );

    @PUT("/product/edit/{id}")
    Call<ResponseModel> editProductById(
            @Path("id") int id,
            @Body ProductModel productModel
    );

    @PUT("/user/edit")
    Call<ResponseModel> editUser(
            @Body UserAccountModel userAccountModel
    );

    @FormUrlEncoded
    @PUT("/implementers/edit")
    Call<ResponseModel> editImplementerCategory(
            @Field("idUserAccount") int userAccountId,
            @Field("idCategory") int categoryId
    );


    @GET("/implementers/{id}/categories")
    Call<CategoriesModel> getImplementerCategories(
            @Path("id") int userId
    );

    @POST("/implementers/{id}/add")
    Call<ResponseModel> assignManyCategoriesToImplementer(
            @Path("id") int userId,
            @Body CategoriesModel categoriesModel
    );

    @GET("/implementers/order/{id}")
    Call<List<ImplementerModel>> getImplementersForOrder(
            @Path("id") int orderId
    );

    @POST("/order/accept/{id}")
    Call<ResponseModel> acceptOrderById(
            @Path("id") int orderId,
            @Body List<Integer> implementersId
    );

    @GET("/user/my")
    Call<UserAccountModel> getMyAccountDetails();

    @POST("/order/reject/{id}")
    Call<ResponseModel> rejectOrderById(
            @Path("id") int orderId,
            @Body String rejectReason
    );

    @GET("/order/current")
    Call<List<OrderModel>> getAllCurrentUserOrders();

    @GET("/order/history")
    Call<List<OrderModel>> getUserOrderHistory();

    @FormUrlEncoded
    @PUT("/user/change/password")
    Call<ResponseModel> changeUserAccountPassword(
            @Field("oldPassword") String oldPassword,
            @Field("newPassword") String newPassword
    );

    @GET("/user/help")
    Call<UserAccountModel> getAdministratorHelpInfo();

}
