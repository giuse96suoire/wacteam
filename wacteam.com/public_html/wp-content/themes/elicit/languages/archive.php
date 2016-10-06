<?php
/**
 * @package elicit
 */
get_header(); ?>
	<div class="content-area col-sm-12 col-md-8">
		<?php if (have_posts()) : ?>
		<?php while (have_posts()) : the_post(); ?>

		<div class="blog-post">

			<h2>
			<span class="blog-post-title"><a href="<?php the_permalink() ?>" rel="bookmark" title="<?php printf(__('Permanent Link to %s', 'elicit'), the_title_attribute('echo=0')); ?>">
			<?php the_title(); ?>
			</a></span>
			</h2>
			<?php $icons = array(); ?>
			<?php if (!is_page()): ?><i class="fa fa-calendar"></i> <?php the_time(__('F jS, Y', 'elicit')) ?>
			<?php endif; ?><?php if (!is_page()): ?><i class="fa fa-user"></i> <a href='<?php echo esc_url( get_author_posts_url( get_the_author_meta( 'ID' ) ) ); ?>'><?php the_author() ?></a>&nbsp
			<?php endif; ?><?php if (current_user_can('edit_post', $post->ID)): ?><i class="fa fa-pencil-square-o"></i> <?php edit_post_link(__('Edit', 'elicit')) ?>			
			<?php endif; ?><?php if (!is_page()): ?><i class="fa fa-folder-open"></i><?php printf(__(' %s', 'elicit'), get_the_category_list(', ')); ?>
			<?php endif; ?><?php if (!is_page() && !is_single()): ?><i class="fa fa-comments"></i> <?php comments_popup_link(__('No Comments &#187;', 'elicit'), __('1 Comment &#187;', 'elicit'), __('% Comments &#187;', 'elicit'), '', __('Comments Closed', 'elicit') ); ?>
			<div class="postthumbnail">
			<a href="<?php echo get_permalink() ?>"><?php the_post_thumbnail('postthumbnail'); ?></a>
			</div>
			<?php if ( get_theme_mod( 'bl_post_excerpt','1' ) ) {
			the_excerpt();
		
			} else {
			the_content( '' );
			} ?>
			<?php endif; ?>
		</div>
		<?php endwhile; ?>

		<?php

		$prev_link = get_previous_posts_link(__('&laquo; Newer Entries', 'elicit'));
		$next_link = get_next_posts_link(__('Older Entries &raquo;', 'elicit'));
		?>

		<?php if ($prev_link || $next_link): ?>

		<div class="navigation" >

	<div class="singleright ">
	<div><?php echo $next_link; ?></div>
	</div>
		<div   class="singleleft">
	<div><?php echo $prev_link; ?></div>
	</div>
	</div>
<div class="cleared"></div>


		<?php endif; ?>
		<?php else : ?>
		<h2 class="center"><?php _e('Not Found', 'elicit'); ?></h2>
		<p class="center"><?php _e('Sorry, but you are looking for something that isn&#8217;t here.', 'elicit'); ?></p>
		<?php if(function_exists('get_search_form')) get_search_form(); ?>
		<?php endif; ?>

	</div>
	<div class="col-sm-4">
	<div class="cleared"></div>
<?php get_sidebar(); ?>


	</div>

	<div class="cleared"></div>
	<?php get_footer(); ?>