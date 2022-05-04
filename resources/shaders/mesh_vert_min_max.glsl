#version 420

layout(location = 0) in vec3 vertex_position;

out vec3 pos;
out vec3 view_pos;

out float out_min;
out float out_max;

uniform mat4 projection;

uniform float min;
uniform float max;

/* proj_to_pos */

void main() {
    pos = vertex_position;
    view_pos = proj_to_pos(projection);
    out_max = max;
    out_min = min;
    gl_Position = projection * vec4(vertex_position, 1);
}