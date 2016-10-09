<?php

/**

 * @package elicit

 */

get_header(); ?>


	<div class="content-area col-sm-12 col-md-8">

		<?php if (have_posts()) : ?>

		<?php while (have_posts()) : the_post(); ?>

			<div class='post-title'>

			<h2>

			<span class="blog-post-title"><a href="<?php the_permalink() ?>" rel="bookmark" title="<?php printf(__('Permanent Link to %s', 'elicit'), the_title_attribute('echo=0')); ?>">

			<?php the_title(); ?>

			</a></span>

			</h2>

			</div>

		<div class="blog-post">



			

			<div class="metadata">

				<?php $icons = array(); ?>

				<?php if (!is_page()): ?><span class="thetime updated"><i class="fa fa-calendar"></i><?php the_time(' G:i d/m/Y'); ?> </span>

				<?php endif; ?><?php if (!is_page()): ?><span class="theauthor"><i class="fa fa-user"></i> <a href='<?php echo esc_url( get_author_posts_url( get_the_author_meta( 'ID' ) ) ); ?>'><?php the_author() ?></a></span>

				<?php endif; ?><?php if (current_user_can('edit_post', $post->ID)): ?><span class="post-edit"><i class="fa fa-pencil-square-o"></i><?php edit_post_link(__(' Edit', 'elicit')) ?></span>		

				<?php endif; ?><?php if (!is_page()): ?><span class="thecategory"><i class="fa fa-folder-open"></i><?php printf(__(' %s', 'elicit'), get_the_category_list(', ')); ?></span>
				
				<?php endif; ?><?php if (!is_page() && !is_single()): ?><span class="thecomment"><i class="fa fa-comments"></i>  <span class="fb-comments-count" data-href="<?php the_permalink() ?>"></span></span>
				
				

				

			</div>

			<div class="postthumbnail">

			<a href="<?php echo get_permalink() ?>"><?php the_post_thumbnail('postthumbnail'); ?></a>

			</div>

			<?php if ( get_theme_mod( 'bl_post_excerpt','1' ) ) {

			the_excerpt();

			// echo '<a href="' . get_permalink() . '"><br><button class="Button" type="button"> Read More </button></a>';

			} else {

			the_content( '' );

			} ?>
			<span  class="fb-like" style="float: right" data-href="<?php the_permalink() ?>" data-layout="button_count" data-action="like" data-size="small" data-show-faces="true" data-share="true"></span><span class="fb-save" data-uri="<?php the_permalink() ?>" data-size="large"></span>
					
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