<?php 
/**
 * Template Name: Full-width
 * @package elicit
 */

get_header(); ?>
<div class="container-fluid blog-main">

<?php if (have_posts()) : while (have_posts()) : the_post(); ?>
<?php
$prev_link = get_previous_post_link('&laquo; %link');
$next_link = get_next_post_link('%link &raquo;');
?>
<?php if ($prev_link || $next_link): ?>

<?php endif; ?>

<div class="blog-post">

<h2 class="blog-post-title">
  
<span ><a href="<?php the_permalink() ?>" rel="bookmark" title="<?php printf(__('Permanent Link to %s', 'elicit'), the_title_attribute('echo=0')); ?>">
<?php the_title(); ?>
</a></span>
</h2>
<div class="meta-tag">
<div class="cleared"></div>
<?php $icons = array(); ?>
<?php if (current_user_can('edit_post', $post->ID)): ?><i class="fa fa-pencil-square-o"></i> <?php edit_post_link(__('Edit', 'elicit')) ?>			
<?php endif; ?>
<div>
<?php if (is_search()) the_excerpt(); else the_content(__('Read the rest of this entry &raquo;', 'elicit')); ?>
</div>

</div>
</div>

<div class="cleared"></div>

<?php endwhile; ?>
<?php else: ?>
<p><?php _e('Sorry, no posts matched your criteria.', 'elicit'); ?></p>
<?php endif; ?>

</div>

</div>
<div class="cleared"></div>

<?php get_footer(); ?>