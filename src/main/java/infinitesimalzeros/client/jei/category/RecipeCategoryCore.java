package infinitesimalzeros.client.jei.category;

import infinitesimalzeros.InfinitesimalZeros;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

public abstract class RecipeCategoryCore <T extends IRecipeWrapper> implements IRecipeCategory<T> {

	protected IDrawableStatic background;
	protected IDrawableStatic icon;
	protected String name;

	@Override
	public String getTitle() {
		
		return name;
	}

	@Override
	public String getModName() {
		
		return InfinitesimalZeros.NAME;
	}

	@Override
	public IDrawable getBackground() {
		
		return background;
	}

	@Override
	public IDrawable getIcon() {
		
		return icon;
	}
	
	
	
}
