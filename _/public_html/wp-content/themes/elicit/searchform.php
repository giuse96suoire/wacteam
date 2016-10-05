<?php
/**
 * @package Just Press
 */
?>
<form method="get" class="search-form" action="<?php echo esc_url( home_url() ); ?>/">
<span class="screen-reader-text"> <?php _e('Search for:', 'elicit'); ?></span>

<input class="search-field" name="s" type="text" value="<?php the_search_query(); ?>" placeholder="<?php echo esc_attr_x( 'Search for&hellip;', 'placeholder', 'elicit' ); ?>" />
<button  class="search-submit" type="submit" name="search">
    <span class="glyphicon glyphicon-search"></span>
</button>
</form>