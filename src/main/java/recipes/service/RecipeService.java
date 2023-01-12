package recipes.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.model.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public interface RecipeService {

    Recipe save(Recipe recipe, User loggedUser);

    Optional<Recipe> getRecipeById(int id);

    HttpStatus deleteRecipeById(int id, User loggedUser);

    HttpStatus updateRecipeById(int id, Recipe recipe, User loggedUser);

    List<Recipe> searchRecipes(Optional<String> category, Optional<String> name);
}
