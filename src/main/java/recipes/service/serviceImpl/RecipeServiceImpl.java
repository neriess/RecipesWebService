package recipes.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.repository.RecipeRepository;
import recipes.service.RecipeService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public Recipe save(Recipe recipe, User loggedUser) {
        recipe.setUser(loggedUser);
        return recipeRepository.save(recipe);
    }

    @Override
    public Optional<Recipe> getRecipeById(int id) {
        return recipeRepository.findById(id);
    }

    @Override
    public HttpStatus deleteRecipeById(int id, User loggedUser) {
        boolean exist = recipeRepository.existsById(id);

        if (exist) {

            User author = recipeRepository.getById(id).getUser();

            if (!Objects.equals(author.getEmail(), loggedUser.getEmail())) {
                return HttpStatus.FORBIDDEN;
            }

            recipeRepository.deleteById(id);
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public HttpStatus updateRecipeById(int id, Recipe recipe, User loggedUser) {
        boolean exist = recipeRepository.existsById(id);

        if (exist) {
            User author = recipeRepository.getById(id).getUser();

            if (!Objects.equals(author.getEmail(), loggedUser.getEmail())) {
                return HttpStatus.FORBIDDEN;
            }

            recipe.setId(id);
            recipeRepository.save(recipe);
            return HttpStatus.NO_CONTENT;
        }
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public List<Recipe> searchRecipes(Optional<String> category, Optional<String> name) {

        if (category.isPresent()) {
            return recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category.get());
        }
        return recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name.get());
    }
}
