<?php

class elicit_popular_posts extends WP_Widget {

	public function __construct() {
		parent::__construct(
	 		'elicit_popular_posts',
			esc_html__('Elicit Popular Posts','elicit'),
			array( 'description' => esc_html__( 'Displays most Popular Posts with Thumbnail.','elicit' ) )
		);
	}

 	public function form( $instance ) {
		$defaults = array(
			'date' => 1,
			'show_thumb3' => 1,
		);
		$instance = wp_parse_args((array) $instance, $defaults);
		$title = isset( $instance[ 'title' ] ) ? $instance[ 'title' ] : __( 'Popular Posts','elicit' );
		$qty = isset( $instance[ 'qty' ] ) ? intval( $instance[ 'qty' ] ) : 5;
		$date = isset( $instance[ 'date' ] ) ? intval( $instance[ 'date' ] ) : 1;
		$show_thumb3 = isset( $instance[ 'show_thumb3' ] ) ? intval( $instance[ 'show_thumb3' ] ) : 1;
		?>
		<p>
			<label for="<?php echo $this->get_field_id( 'title' ); ?>"><?php _e( 'Title:','elicit' ); ?></label> 
			<input class="widefat" id="<?php echo $this->get_field_id( 'title' ); ?>" name="<?php echo $this->get_field_name( 'title' ); ?>" type="text" value="<?php echo esc_attr( $title ); ?>" />
		</p>
			   
		<p>
			<label for="<?php echo $this->get_field_id( 'qty' ); ?>"><?php _e( 'Number of Posts to show','elicit' ); ?></label> 
			<input id="<?php echo $this->get_field_id( 'qty' ); ?>" name="<?php echo $this->get_field_name( 'qty' ); ?>" type="number" min="1" step="1" value="<?php echo $qty; ?>" />
		</p>
		
		<p>
			<label for="<?php echo $this->get_field_id("show_thumb3"); ?>">
				<input type="checkbox" class="checkbox" id="<?php echo $this->get_field_id("show_thumb3"); ?>" name="<?php echo $this->get_field_name("show_thumb3"); ?>" value="1" <?php if (isset($instance['show_thumb3'])) { checked( 1, $instance['show_thumb3'], true ); } ?> />
				<?php _e( 'Show Thumbnails', 'elicit'); ?>
			</label>
		</p>
		
		<p>
			<label for="<?php echo $this->get_field_id("date"); ?>">
				<input type="checkbox" class="checkbox" id="<?php echo $this->get_field_id("date"); ?>" name="<?php echo $this->get_field_name("date"); ?>" value="1" <?php if (isset($instance['date'])) { checked( 1, $instance['date'], true ); } ?> />
				<?php _e( 'Show post date', 'elicit'); ?>
			</label>
		</p>
		

		<?php 
	}

	public function update( $new_instance, $old_instance ) {
		$instance = array();
		$instance['title'] = strip_tags( $new_instance['title'] );
		$instance['qty'] = intval( $new_instance['qty'] );
		$instance['date'] = intval( $new_instance['date'] );
		$instance['show_thumb3'] = intval( $new_instance['show_thumb3'] );
		return $instance;
	}

	public function widget( $args, $instance ) {
		extract( $args );
		$title = apply_filters( 'widget_title', $instance['title'] );
		$date = $instance['date'];
		$qty = (int) $instance['qty'];
		$show_thumb3 = (int) $instance['show_thumb3'];

		echo $before_widget;
		if ( ! empty( $title ) ) echo $before_title . $title . $after_title;
		echo self::get_popular_posts( $qty, $date, $show_thumb3);
		echo $after_widget;
	}

	public function get_popular_posts( $qty,$date, $show_thumb3) {
		global $post;
		$popular = get_posts( array( 
            'suppress_filters' => false, 
            'ignore_sticky_posts' => 1, 
            'orderby' => 'comment_count', 
            'numberposts' => $qty,
            ) );
            
		echo '<ul class="popular-posts">';
		foreach($popular as $post) :
			setup_postdata($post);
			?>
		<li>
			<a href="<?php the_permalink(); ?>">
				<?php if ( $show_thumb3 == 1 ) : ?>
				    <?php the_post_thumbnail('elicit-small',array('title' => '')); ?>
				<?php endif; ?>
				<?php the_title(); ?>	
			</a>
			<div class="meta">
				<?php if ( $date == 1 ) : ?>
					<?php the_time('F j, Y'); ?>
				<?php endif; ?>
			</div> <!--end .entry-meta--> 	

		</li>	
		<?php endforeach; wp_reset_postdata();
		echo '</ul>'."\r\n";
	}

}
add_action( 'widgets_init', create_function( '', 'register_widget( "elicit_popular_posts" );' ) );