#version 420

in vec3 pos;
in vec3 view_pos;
in float out_max;
in float out_min;

out vec4 frag_color;

/* fog */
/* levels */

void main() {
    float axisK = levels(pos, .002, .002 + .001 / gl_FragCoord.w, 10, vec3(0, 1, 0));

    float blue = 1 - ((pos.y - out_min) / (out_max - out_min));
    float green = ((pos.y - out_min) / (out_max - out_min));

    vec4 color = mix(vec4(0, 0.5 * green, 0.5 * blue, 1), vec4(0, green, blue, 1), axisK);

    frag_color = fog(color, pos, view_pos);
}