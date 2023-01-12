package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.service.RecipeService;
import recipes.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable int id) {
        return ResponseEntity.of(recipeService.getRecipeById(id));
    }

    @PostMapping("/new")
    public Map<String, Integer> saveRecipe(@Valid @RequestBody Recipe recipe, Principal principal) {
        User loggedUser = userService.getUserByEmail(principal.getName());
        Recipe savedRecipe = recipeService.save(recipe, loggedUser);
        return Map.of("id", savedRecipe.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable int id, Principal principal) {
        User loggedUser = userService.getUserByEmail(principal.getName());
        return new ResponseEntity<>(recipeService.deleteRecipeById(id, loggedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateRecipe(@PathVariable int id, @Valid @RequestBody Recipe recipe,
                                                   Principal principal) {
        User loggedUser = userService.getUserByEmail(principal.getName());
        return new ResponseEntity<>(recipeService.updateRecipeById(id, recipe, loggedUser));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> getRecipesBySearch(@RequestParam Optional<String> category,
                                                           @RequestParam Optional<String> name) {
        if (category.isPresent() && name.isPresent() || category.isEmpty() && name.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(recipeService.searchRecipes(category, name), HttpStatus.OK);
    }
}
