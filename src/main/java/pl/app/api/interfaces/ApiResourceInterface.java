package pl.app.api.interfaces;

import pl.app.api.model.CategoriesModel;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ApiResourceInterface {

    @GET("/categories")
    Call<List<CategoriesModel>> getAllCategories();

}
