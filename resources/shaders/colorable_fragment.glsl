#version 420

in vec3 pos;
in float out_max;
in float out_min;

out vec4 frag_color;

void main() {
    float width = 0.02f;

    float k = pos.y - floor(pos.y);

    float blue = 1 - ((pos.y - out_min) / (out_max - out_min));
    float green = ((pos.y - out_min) / (out_max - out_min));

    if (pos.y * 10 - floor(pos.y * 10) < 0.07) {
        frag_color = vec4( 0, 0.5 * green, 0.5 * blue, 1);
    } else {
        frag_color = vec4(0, green, blue, 1);
    }
}